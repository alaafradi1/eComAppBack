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
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.service.CaisseService;
import com.eCommerce.eCommerceApp.service.CompanyService;
import com.eCommerce.eCommerceApp.service.DepenceGCService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class CompanyController {

    @Autowired
	CompanyService cs;

    @PostMapping("/addCompany")
	public ResponseEntity<String> addCompany(@RequestBody Company c) {
		try {
			// Get the current date and time
			Calendar calendar = Calendar.getInstance();
			c.setCreationDate(calendar.getTime());

			cs.addCompany(c);
			return ResponseEntity.ok("Company added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Company: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

    @GetMapping("/getAllCompanies")
	public ResponseEntity<List<Company>> getAllCompanies() {
        try {
            List<Company> companies = cs.getAllCompanies();
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteCompany/{idCompany}")
	public void deleteCompany(@PathVariable Long idCompany) {
		cs.deleteCompany(idCompany);
	}
    
}
