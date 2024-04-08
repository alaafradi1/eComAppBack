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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CompanyRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;
// import com.eCommerce.eCommerceApp.repository.ProductRepository;
import com.eCommerce.eCommerceApp.service.CaisseService;
import com.eCommerce.eCommerceApp.service.ProductService;

@RestController
@CrossOrigin("*")
public class ProductController {

	@Autowired
	ProductService ps;
	@Autowired
	ProductRepository pr;
	@Autowired
	CompanyRepository cr;

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

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		try {
			List<Product> products = ps.getAllProducts();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getActiveProducts")
	public ResponseEntity<List<Product>> getActiveProducts() {
		try {
			List<Product> products = ps.getActiveProducts();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// @PutMapping("/editProduct/{productId}")
	// public ResponseEntity<String> editProduct(@PathVariable Long productId,@RequestBody Map<String, String> productWithCompanyId) {
	// 	try {
	// 		Product existingProduct = pr.findById(productId).get();
	// 		if (existingProduct == null) {
	// 			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found using productId");
	// 		}

	// 		existingProduct.setProductName(productWithCompanyId.get("productName"));
	// 		existingProduct.setRowColor(productWithCompanyId.get("rowColor"));
	// 		existingProduct.setProductPrice(Float.parseFloat(productWithCompanyId.get("productPrice")));
	// 		existingProduct.setProductCost(Float.parseFloat(productWithCompanyId.get("productCost")));
	// 		Company newCompany = cr.findById(Long.parseLong(productWithCompanyId.get("idCompany"))).get();
	// 		existingProduct.setCompany(newCompany);
	// 		// Update other fields as needed

	// 		// Save the updated product back to the database
	// 		ps.editProduct(existingProduct);

	// 		return ResponseEntity.ok("Product updated successfully");
	// 	} catch (Exception e) {
	// 		String errorMessage = "Error updating product: " + e.getMessage();
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	// 	}
	// }

	@PutMapping("/editProduct/{productId}")
	public ResponseEntity<String> editProduct(@PathVariable Long productId,@RequestBody Map<String, String> productWithCompanyId) {
		try {
			// Product existingProduct = pr.findById(productId).get();
			// if (existingProduct == null) {
			// 	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found using productId");
			// }

			// existingProduct.setProductName(productWithCompanyId.get("productName"));
			// existingProduct.setRowColor(productWithCompanyId.get("rowColor"));
			// existingProduct.setProductPrice(Float.parseFloat(productWithCompanyId.get("productPrice")));
			// existingProduct.setProductCost(Float.parseFloat(productWithCompanyId.get("productCost")));
			// Company newCompany = cr.findById(Long.parseLong(productWithCompanyId.get("idCompany"))).get();
			// existingProduct.setCompany(newCompany);
			// // Update other fields as needed

			// // Save the updated product back to the database
			ResponseEntity response = ps.editProduct( productId,productWithCompanyId);

			//return ResponseEntity.ok("Product updated successfully");
			return response ;
		} catch (Exception e) {
			String errorMessage = "Error updating product: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/activateProduct/{productId}")
	public ResponseEntity<String> activateProduct(@PathVariable Long productId,@RequestBody Map<String, String> productWithCompanyId) {
		try {
			Product existingProduct = pr.findById(productId).get();
			if (existingProduct == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}

			existingProduct.setIsActive(Boolean.parseBoolean(productWithCompanyId.get("isActive")));
			
			// Update other fields as needed

			// Save the updated product back to the database
			// ps.editProduct(existingProduct);
			ps.activateProduct(existingProduct);

			return ResponseEntity.ok("Product activation successfully");
		} catch (Exception e) {
			String errorMessage = "Error activation product: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

}
