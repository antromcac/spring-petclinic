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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.DiseaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

/*
	@GetMapping("enfermedadesList")
	public String mostrarEnfermedades(final ModelMap modelMap) {
		String vista = "enfermedades/enfermedadesList";
		Iterable<Enfermedad> enfermedades = this.enfermedadService.findAll();
		modelMap.addAttribute("enfermedades", enfermedades);
		return vista;
	}*/
	/*
	@PostMapping("/enfermedades")
	public Enfermedad crearAdministrador(@RequestBody Enfermedad enfermedad ) {
		enfermedad = enfermedadService.save(enfermedad);
		return enfermedad;
	}
*/
}