package com.eCommerce.eCommerceApp.controllers;

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

import com.eCommerce.eCommerceApp.entity.ProdDetail;
import com.eCommerce.eCommerceApp.entity.Produit;
import com.eCommerce.eCommerceApp.service.ProdDetailService;

@RestController
@CrossOrigin("*")
public class ProdDetailController {

	@Autowired
	ProdDetailService pds;
	
	@GetMapping("/allProdDetail")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List <ProdDetail> getAllProdDetail(){
		return pds.getAllProdDetail();
	}
	
	@PostMapping("/addProdDetail")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void addProdDetail(@RequestBody ProdDetail c) {
		c.setRevenueUnitaire(c.getRevenueUnitaire());
		c.setPrixSansLivraison(c.getPrixSansLivraison());
		pds.addProdDetail(c);
	}
	
	@PutMapping("/updateProdDetail")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void editProdDetail(@RequestBody ProdDetail c) {
		c.setRevenueUnitaire(c.getRevenueUnitaire());
		c.setPrixSansLivraison(c.getPrixSansLivraison());
		pds.editProdDetail(c);
	}
	
	@DeleteMapping("/deleteProdDetail/{id}")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void deleteProdDetail(@PathVariable long id) {
		pds.deleteProdDetail(id);
	}
	

}
