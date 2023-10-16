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

import com.eCommerce.eCommerceApp.entity.ProdDetail;
import com.eCommerce.eCommerceApp.entity.Produit;
import com.eCommerce.eCommerceApp.service.CaisseService;
import com.eCommerce.eCommerceApp.service.ProduitService;

@RestController
@CrossOrigin(origins = "*")
public class ProduitController {

	@Autowired
	ProduitService ps;
	
	@Autowired
	CaisseService cs;
	
	@GetMapping("/allProduit")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public List <Produit> getAllProduit(){
		System.out.print("*****************************");
		return ps.getAllProduits();
	}
	
	@PostMapping("/addProduit")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void addProduit(@RequestBody Produit c) {
		c.setPourcentageRetour();
		c.setRevenue();
		c.getProduitDetail().setRevenueUnitaire(c.getProduitDetail().getRevenueUnitaire());
		c.getCaisse().setRevenueTotale(cs.getTotalRevenue(c.getCaisse().getProductName()));
		ps.addProduit(c);
	}
	
	@PutMapping("/updateProduit")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void editProduit(@RequestBody Produit c) {
		c.setPourcentageRetour();
		c.setRevenue();
		c.getProduitDetail().setRevenueUnitaire(c.getProduitDetail().getRevenueUnitaire());
		c.getCaisse().setRevenueTotale(cs.getTotalRevenue(c.getCaisse().getProductName()));
		ps.editProduit(c);
	}
	
	@DeleteMapping("/deleteProduit/{id}")
//	@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	public void deleteProduit(@PathVariable int id) {
		ps.deleteProduit(id);
	}
}
