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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
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

	@GetMapping("/getStatisticsByCriteria")
	public ResponseEntity<Map<String, String>> getStatisticsByCriteria(@RequestParam("criteria") String criteria ) {
		try {
			Map<String, String> result = cs.getStatisticsByCriteria(criteria);
            return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getCaisseByDateRange")
	public ResponseEntity<List<Caisse>> getCaisseByDateRange(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		try {
			List<Caisse> caissesList = cs.getCaisseByDateRange(startDate, endDate);
			return ResponseEntity.ok(caissesList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PostMapping("/addCaisse")
	public ResponseEntity<String> addCaisse(@RequestBody Map<String, String> caisseInfo) {
		try {
			cs.addCaisse(caisseInfo);
			return ResponseEntity.ok("Caisse added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Caisse: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PostMapping("/addMultipleCaisses")
	public ResponseEntity<String> addMultipleCaisses(@RequestBody List<Map<String, String>> caissesInfo) {
		try {
			cs.addMultipleCaisses(caissesInfo);
			return ResponseEntity.ok("Multiple Caisses added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Multiple CAisses: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}



	@DeleteMapping("/deleteCaisse/{idCaisse}")
	public ResponseEntity<String> deleteCaisse(@PathVariable Long idCaisse) {
		try {
			cs.deleteCaisse(idCaisse);
			return ResponseEntity.ok("Caisse deleted successfully");
		} catch (Exception e) {
			String errorMessage = "Error deleting Caisse: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/editCaisse/{caisseId}")
	public ResponseEntity<String> editCaisse(@PathVariable Long caisseId, @RequestBody Map<String, String> caisseInfo) {
		try {
			cs.editCaisse(caisseId, caisseInfo);
			return ResponseEntity.ok("Caisse updated successfully");
		} catch (Exception e) {
			String errorMessage = "Error updating Caisse: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
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
