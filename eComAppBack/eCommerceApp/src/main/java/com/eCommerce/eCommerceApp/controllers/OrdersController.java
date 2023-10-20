package com.eCommerce.eCommerceApp.controllers;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.service.OrdersService;
import com.eCommerce.eCommerceApp.service.ProductService;

@RestController
@CrossOrigin("*")
public class OrdersController {
    @Autowired
	OrdersService os;

	@PostMapping("/addOrder")
	public ResponseEntity<String> addOrder(@RequestBody Map<String, String> orderInfo) {
        try {
		os.addOrder(orderInfo);
		return ResponseEntity.ok("Product added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Product: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
    }

     




}
