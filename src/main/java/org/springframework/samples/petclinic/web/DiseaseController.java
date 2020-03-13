/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.DiseaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.samples.petclinic.service.PetService;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/diseases")
public class DiseaseController {

	@Autowired
	private DiseaseService DiseaseService;

	@Autowired
	private PetService PetService;

	@GetMapping("/diseasesList")
	public Iterable<Disease> findDiseases(final ModelMap modelMap) {

		Iterable<Disease> diseases = this.DiseaseService.findAll();
		modelMap.addAttribute("diseases", diseases);
		return this.DiseaseService.findAll();
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePet() {
		return this.DiseaseService.findPets();
	}

	@GetMapping(value = "/new")
	public String NewDiseases(Map<String, Object> model) {
		Disease disease = new Disease();
		Collection<Pet> pets = DiseaseService.findPets();
		model.put("diseases", disease);
		model.put("pets", pets);
		return "diseases/createOrUpdateDiseaseForm";
	}

	/*
	@PostMapping("/enfermedades")
	public Enfermedad crearAdministrador(@RequestBody Enfermedad enfermedad ) {
		enfermedad = enfermedadService.save(enfermedad);
		return enfermedad;
	}
*/
}
