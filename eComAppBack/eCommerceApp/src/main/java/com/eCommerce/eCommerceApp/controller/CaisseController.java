package com.eCommerce.eCommerceApp.controller;

import java.util.List;
import org.springframework.ui.Model;


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
import com.eCommerce.eCommerceApp.service.CaisseService;
import com.eCommerce.eCommerceApp.service.DepenceGCService;

@RestController
@CrossOrigin("*")
public class CaisseController {

	@Autowired
	CaisseService cs;
	
	@GetMapping("/allCaisseInfo")
	public List <Caisse> getAllInfo(){
		return cs.getAllInfos();
	}
	
	@PostMapping("/addCaisseInfo")
	public void addCaisseInfo(@RequestBody Caisse c) {
		c.setRevenueTotale(cs.getTotalRevenue(c.getProductName()));
		cs.addInfo(c);
	}
	
	@PutMapping("/updateCaisseInfo")
	public void editCaisseInfo(@RequestBody Caisse c) {
		c.setRevenueTotale(cs.getTotalRevenue(c.getProductName())-cs.getMontant(c.getProductName()));
		cs.editInfo(c);
	}
	
	@DeleteMapping("/deleteCaisseInfo/{id}")
	public void deleteCaisseInfo(@PathVariable int id) {
		cs.deleteInfo(id);
	}
}
