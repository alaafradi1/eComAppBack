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

import com.eCommerce.eCommerceApp.entity.CreerColis;
import com.eCommerce.eCommerceApp.service.CreerColisService;

@RestController
@CrossOrigin("*")
public class CreerColisController {
	
	@Autowired
	CreerColisService ccsv;
	
	@GetMapping("/allCreerColis")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List <CreerColis> getAllCreerColis(){
		return ccsv.getAllCreerColis();
	}
	
	@PostMapping("/addCreerColis")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void addCreerColis(@RequestBody CreerColis c) {
		ccsv.addCreerColis(c);
	}
	
	@PutMapping("/updateCreerColis")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void editCreerColis(@RequestBody CreerColis c) {
		ccsv.editCreerColis(c);
	}
	
	@DeleteMapping("/deleteCreerColis/{id}")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void deleteCreerColis(@PathVariable int id) {
		ccsv.deleteCreerColis(id);
	}
}
