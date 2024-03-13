package com.eCommerce.eCommerceApp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Compaign;
import com.eCommerce.eCommerceApp.service.CompaignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("*")
public class CompaignController {

    @Autowired
    CompaignService cs;

	@GetMapping("/getAllCompaigns")
	public List<Compaign> getAllCompaigns() {
		return cs.getAllCompaigns();
	}
	

    @PostMapping("/addCompaign")
	public ResponseEntity<String> addCompaign(@RequestBody Map<String, String> CompaignInfo) {
		try {
			cs.addCompaignToProduct(CompaignInfo);
			return ResponseEntity.ok("Compaign added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Compaign: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
    
    
}
