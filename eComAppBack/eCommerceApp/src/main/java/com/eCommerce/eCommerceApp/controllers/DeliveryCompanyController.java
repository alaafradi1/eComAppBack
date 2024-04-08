package com.eCommerce.eCommerceApp.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.repository.DeliveryCompanyRepository;
import com.eCommerce.eCommerceApp.service.DeliveryCompanyService;

@RestController
@CrossOrigin("*")
public class DeliveryCompanyController {
    @Autowired
    DeliveryCompanyService dcs;

    @Autowired
    DeliveryCompanyRepository dcr;

    @PostMapping("/addDeliveryCompany")
	public ResponseEntity<String> addDeliveryCompany(@RequestBody DeliveryCompany dc) {
		try {

            Calendar calendar = Calendar.getInstance();
			dc.setCreationDate(calendar.getTime());
			dc.setIsActive(true);

			dcs.addDeliveryCompany(dc);
			return ResponseEntity.ok("DeliveryCompany added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding DeliveryCompany: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

    @GetMapping("/getAllDeliveryCompanies")
	public ResponseEntity<List<DeliveryCompany>> getAllDeliveryCompanies() {
        try {
            List<DeliveryCompany> DeliveryCompanies = dcs.getAllDeliveryCompanies();
            return ResponseEntity.ok(DeliveryCompanies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	@GetMapping("/getActiveDeliveryCompanies")
	public ResponseEntity<List<DeliveryCompany>> getActiveDeliveryCompanies() {
        try {
            List<DeliveryCompany> DeliveryCompanies = dcs.getActiveDeliveryCompanies();
            return ResponseEntity.ok(DeliveryCompanies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateDeliveryCompany/{deliveryCompanyId}")
	public ResponseEntity<String> editCompany(@PathVariable Long deliveryCompanyId,@RequestBody Map<String, String> deliveryCompanyInfo) {
		try {
			DeliveryCompany existingDeliveryCompany = dcr.findById(deliveryCompanyId).get();
			if (existingDeliveryCompany == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery Company not found");
			}
			existingDeliveryCompany.setDeliveryPrice(Float.parseFloat(deliveryCompanyInfo.get("deliveryPrice")));
            existingDeliveryCompany.setName(deliveryCompanyInfo.get("name"));
			existingDeliveryCompany.setRowColor(deliveryCompanyInfo.get("rowColor"));
			dcs.updateDeliveryCompany(existingDeliveryCompany);
			return ResponseEntity.ok(" deliveryCompany updated successfully");
		} catch (Exception e) {
			String errorMessage = "Error updating deliveryCompany: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/activateDeliveryCompany/{deliveryCompanyId}")
	public ResponseEntity<String> activateCompany(@PathVariable Long deliveryCompanyId,@RequestBody Map<String, String> deliveryCompanyInfo) {
		try {
			DeliveryCompany existingDeliveryCompany = dcr.findById(deliveryCompanyId).get();
			if (existingDeliveryCompany == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery Company not found");
			}
			existingDeliveryCompany.setIsActive(Boolean.parseBoolean(deliveryCompanyInfo.get("isActive")));
           
			dcs.updateDeliveryCompany(existingDeliveryCompany);
			return ResponseEntity.ok(" deliveryCompany activation successfully");
		} catch (Exception e) {
			String errorMessage = "Error activation deliveryCompany: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}
    
}
