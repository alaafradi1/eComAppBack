package com.eCommerce.eCommerceApp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class CaisseController {

	@Autowired
	CaisseService cs;

	@GetMapping("/getAllCaisses")
	public ResponseEntity<List<Caisse>> getAllCaisses() {
        try {
            List<Caisse> caisses = cs.getAllCaisses();
            return ResponseEntity.ok(caisses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	@PostMapping("/addCaisse")
	public ResponseEntity<String> addCaisse(@RequestBody Caisse c) {
		try {
			// Get the current date and time
			Calendar calendar = Calendar.getInstance();
			c.setCreationDate(calendar.getTime());

			cs.addCaisse(c);
			return ResponseEntity.ok("Caisse added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Caisse: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@DeleteMapping("/deleteCaisse/{idCaisse}")
	public void deleteCaisse(@PathVariable Long idCaisse,@RequestBody Map<String, String> reason) {
		cs.deleteCaisse(idCaisse, reason.get("reason"));
	}

	// @PostMapping("/addCaisseInfo")
	// public void addCaisseInfo(@RequestBody Caisse c) {
	// c.setRevenueTotale(cs.getTotalRevenue(c.getProductName()));
	// cs.addInfo(c);
	// }

	// @PutMapping("/updateCaisseInfo")
	// public void editCaisseInfo(@RequestBody Caisse c) {
	// c.setRevenueTotale(cs.getTotalRevenue(c.getProductName())-cs.getMontant(c.getProductName()));
	// cs.editInfo(c);
	// }

	// @DeleteMapping("/deleteCaisseInfo/{id}")
	// public void deleteCaisseInfo(@PathVariable int id) {
	// cs.deleteInfo(id);
	// }
}
