package com.eCommerce.eCommerceApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.DepenceGC;
import com.eCommerce.eCommerceApp.service.DepenceGCService;

@RestController
@CrossOrigin("*")
public class DepenceGCController {
	
	@Autowired
	DepenceGCService dgcs;
	
	@GetMapping("/allDepenceInfo")
	public List <DepenceGC> getAllDepenceInfo(){
		return dgcs.getAllDepencesGC();
	}
	
	@PostMapping("/addDepenceInfo")
	public void addDepenceInfo(@RequestBody DepenceGC c) {
		dgcs.updRevenueCaisse(c.getNomArticle(), c.getMontant());
		dgcs.addDepencesGC(c);
	}
	
	@PutMapping("/updateDepenceInfo")
	public void editDepenceInfo(@RequestBody DepenceGC c) {
		dgcs.updateDepencesGC(c);
	}
	
	@DeleteMapping("/deleteDepenceInfo/{id}")
	public void deleteDepenceInfo(@PathVariable int id) {
		dgcs.deleteDepencesGC(id);
	}
}
