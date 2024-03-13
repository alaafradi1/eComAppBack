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

import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.entity.SourceOrder;
import com.eCommerce.eCommerceApp.repository.SourceOrderRepository;
import com.eCommerce.eCommerceApp.service.DeliveryCompanyService;
import com.eCommerce.eCommerceApp.service.SourceOrderService;

@RestController
@CrossOrigin("*")
public class SourceOrderController {
     @Autowired
    SourceOrderService sos;

    @Autowired
    SourceOrderRepository sor;

     @PostMapping("/addSource")
	public ResponseEntity<String> addSource(@RequestBody SourceOrder so) {
		try {
			sos.addSource(so);
			return ResponseEntity.ok("SourceOrder added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding SourceOrder: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

    @GetMapping("/getAllSources")
	public ResponseEntity<List<SourceOrder>> getAllSources() {
        try {
            List<SourceOrder> SourceOrders = sos.getAllSources();
            return ResponseEntity.ok(SourceOrders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateSource/{sourceOrderId}")
	public ResponseEntity<String> updateSource(@PathVariable Long sourceOrderId,@RequestBody Map<String, String> sourceInfo) {
		try {
		
            sos.updateSource(sourceOrderId,sourceInfo);
			return ResponseEntity.ok("Source Order added successfully");
		} catch (Exception e) {
			String errorMessage = "Error updating Source Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/activateSource/{sourceId}")
	public ResponseEntity<String> activateSource(@PathVariable Long sourceId,@RequestBody Map<String, String> sourceInfo) {
		try {
			SourceOrder existingSourceOrder = sor.findById(sourceId).get();
			if (existingSourceOrder == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Source not found");
			}
			existingSourceOrder.setIsActive(Boolean.parseBoolean(sourceInfo.get("isActive")));
           
			sor.save(existingSourceOrder);
			return ResponseEntity.ok(" Source activation successfully");
		} catch (Exception e) {
			String errorMessage = "Error activation Source: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

    @GetMapping("/getActiveSources")
	public ResponseEntity<List<SourceOrder>> getActiveSources() {
        try {
            List<SourceOrder> sources = sos.getActiveSources();
            return ResponseEntity.ok(sources);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
