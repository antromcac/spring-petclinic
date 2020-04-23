package org.springframework.samples.petclinic.web.endToEnd;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DiseaseControllerE2ETest {
	
	private static final int TEST_PET_ID = 1;
	private static final int TEST_DISEASE_ID = 1;


	@Autowired
	private MockMvc mockMvc;	

   
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
  	@Test
  	void testListDisease() throws Exception {
  		mockMvc.perform(get("/diseases/diseasesList")).
  		andExpect(status().isOk()).
  		andExpect(model().attributeExists("diseases"))
  		.andExpect(view().name("diseases/diseasesList"));
	  }
    
	@WithMockUser(username="admin1",authorities= {"admin"})
  	@Test
  	void testNotListDisease() throws Exception {
  		mockMvc.perform(get("/diseases/diseasesList")).
  		andExpect(status().is4xxClientError()).
  		andExpect(status().reason("Forbidden"));
	  }

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
    @Test
    void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/diseases/new/{petId}",TEST_PET_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("disease"))
		.andExpect(view().name("diseases/createOrUpdateDiseaseForm"))
		.andExpect(model().attributeExists("disease"));
	}

	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void testInitCreationFormFail() throws Exception {
		mockMvc.perform(get("/diseases/new/{petId}",TEST_PET_ID)).andExpect(status().is(403))
		.andExpect(status().reason("Forbidden"));
	}

	@WithMockUser(username="vet1",authorities= {"veterinarian"})
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/diseases/new/{petId}",TEST_PET_ID)
				.requestAttr("petId", TEST_PET_ID)
							.with(csrf())
							.param("symptoms", "Mareos, vomitos y diarrea...")
							.param("severity", "LOW")
							.param("cure", "Unas pastillas"))
				.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/diseases/new/{petId}",TEST_PET_ID)
				.requestAttr("petId", TEST_PET_ID)
							.with(csrf())
							.param("symptoms", "Mareos, vomitos y diarrea...")
							.param("severity", "En duda")
							.param("cure", "Unas pastillas"))
				.andExpect(model().attributeHasErrors("disease"))
				.andExpect(status().is(200))
				.andExpect(view().name("diseases/createOrUpdateDiseaseForm"));
	}
	@WithMockUser(username="vet1",authorities= {"veterinarian"})
	@Test
	void testShowDisease() throws Exception {
		
			mockMvc.perform(get("/diseases/{diseaseId}", TEST_DISEASE_ID).
					requestAttr("diseaseId", 1))
					.andExpect(status().isOk())
					.andExpect(model().attribute("disease", hasProperty("cure", is("malisimo de la muerte"))))
					.andExpect(model().attribute("disease", hasProperty("severity", is("LOW"))))
					.andExpect(model().attribute("disease", hasProperty("symptoms", is("compra paracetamol"))))
					.andExpect(model().attribute("disease", hasProperty("pet")))
					.andExpect(model().attributeExists("disease"))
					.andExpect(view().name("diseases/diseaseDetails"));
					
					
		}

	@WithMockUser(username="admin1",authorities= {"admin"})
	   	@Test
	   	void testShowDiseaseError() throws Exception {
	   		mockMvc.perform(get("/diseases/{diseaseId}",TEST_DISEASE_ID))
			.andExpect(status().is4xxClientError())
			.andExpect(status().reason("Forbidden"));
	   	}
	
	

}