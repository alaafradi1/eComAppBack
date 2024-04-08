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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.service.OrdersService;
import com.eCommerce.eCommerceApp.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.mysql.cj.x.protobuf.MysqlxCrud.Order;

@RestController
@CrossOrigin("*")
public class OrdersController {
	@Autowired
	OrdersService os;

	@PostMapping("/addOrderWithMupltipleProducts")
	public ResponseEntity<String> addOrderWithMupltipleProducts(@RequestBody String orderInfo) {
		try {
			os.addOrderWithMupltipleProducts(orderInfo);
			return ResponseEntity.ok("Order added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/editOrderWithMupltipleProducts")
	public ResponseEntity<String> editOrderWithMupltipleProducts(@RequestBody String orderInfo) {
		try {
			os.editOrderWithMupltipleProducts(orderInfo);
			return ResponseEntity.ok("Order edited successfully");
		} catch (Exception e) {
			String errorMessage = "Error editing Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PostMapping("/addMultipleOrders")
	public ResponseEntity<String> addMultipleOrders(@RequestBody List<Map<String, String>> ordersInfo) {
		try {
			ResponseEntity<String> response = os.addMultipleOrders(ordersInfo); 
			if("success".equals(response)){
				return ResponseEntity.ok("Multiple Orders added successfully");
			} else {
				//return ResponseEntity.status(404).body("Error adding Multiple Orders: "+response);
                       
				return response;
			}
			
			
		} catch (Exception e) {
			String errorMessage = "Error adding Multiple Orders: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@DeleteMapping("/deleteOrdre/{idOrdre}")
	public ResponseEntity<String> deleteOrdre(@PathVariable Long idOrdre) {
		try {
			os.deleteOrdre(idOrdre);
			return ResponseEntity.ok("Order deleted successfully");
		} catch (Exception e) {
			String errorMessage = "Error deleting Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/editMultipleOrders")
	public ResponseEntity<String> editMultipleOrders(@RequestBody JsonNode orderInfo) {
		try {
			os.editMultipleOrderswithMultipleProducts(orderInfo);
			return ResponseEntity.ok("Multiple orders edited successfully");
		} catch (Exception e) {
			String errorMessage = "Error editing multiple orders: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@GetMapping("/getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts")
	public ResponseEntity<List<Orders>> getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		try {
			List<Orders> ordersList = os.getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts(startDate,
					endDate);
			return ResponseEntity.ok(ordersList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PutMapping("/editStatusOfOrders")
	public ResponseEntity<String> editStatusOfOrders(@RequestBody JsonNode orderInfo) {
		try {
			os.editStatusOfOrders(orderInfo);
			return ResponseEntity.ok("Order's Status edited successfully");
		} catch (Exception e) {
			String errorMessage = "Error editing Order's Status: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}


}
