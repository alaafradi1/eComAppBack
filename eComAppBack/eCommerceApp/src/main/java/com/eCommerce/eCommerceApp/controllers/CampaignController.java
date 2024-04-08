package com.eCommerce.eCommerceApp.controllers;

import java.util.List;
import java.util.Map;

import com.eCommerce.eCommerceApp.entity.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eCommerce.eCommerceApp.service.CampaignService;


@RestController
@CrossOrigin("*")
public class CampaignController {

    @Autowired
	CampaignService cs;

	@GetMapping("/getAllCampaigns")
	public List<Campaign> getAllCampaigns() {
		return cs.getAllCampaigns();
	}

	@PutMapping("/editCampaign/{campaignId}")
	public ResponseEntity<String> editCampaign(@PathVariable Long campaignId,@RequestBody Map<String, String> campaignInfo) {
		try {
			cs.editCampaign( campaignId,campaignInfo);

			return ResponseEntity.ok("Campaign updated successfully");
		} catch (Exception e) {
			String errorMessage = "Error updating campaign: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
    @PostMapping("/addCampaign")
	public ResponseEntity<String> addCampaign(@RequestBody Map<String, String> campaignInfo) {
		try {
			cs.addCampaignToProduct(campaignInfo);
			return ResponseEntity.ok("Campaign added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Campaign: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
    
    
}
