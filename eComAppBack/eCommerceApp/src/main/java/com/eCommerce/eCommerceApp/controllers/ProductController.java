package com.eCommerce.eCommerceApp.controllers;

import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.service.CaisseService;
import com.eCommerce.eCommerceApp.service.ProductService;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
	ProductService ps;

    @PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody Map<String, String> productWithCompany) {
        try {
		ps.addProduct(productWithCompany);
		return ResponseEntity.ok("Product added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Product: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
    }
}
