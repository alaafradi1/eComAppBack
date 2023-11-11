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

	@PostMapping("/addOrder")
	public ResponseEntity<String> addOrder(@RequestBody Map<String, String> orderInfo) {
		try {
			os.addOrder(orderInfo);
			return ResponseEntity.ok("Order added successfully");
		} catch (Exception e) {
			String errorMessage = "Error adding Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PostMapping("/addMultipleOrders")
	public ResponseEntity<String> addMultipleOrders(@RequestBody List<Map<String, String>> ordersInfo) {
		try {
			os.addMultipleOrders(ordersInfo);
			return ResponseEntity.ok("Multiple Orders added successfully");
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

	@PutMapping("/editOrder")
	public ResponseEntity<String> editOrder(@RequestBody Map<String, String> orderInfo) {
		try {
			os.editOrder(orderInfo);
			return ResponseEntity.ok("Order edited successfully");
		} catch (Exception e) {
			String errorMessage = "Error editing Order: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@PutMapping("/editMultipleOrders")
	public ResponseEntity<String> editMultipleOrders(@RequestBody JsonNode orderInfo) {
		try {
			os.editMultipleOrders(orderInfo);
			return ResponseEntity.ok("Multiple orders edited successfully");
		} catch (Exception e) {
			String errorMessage = "Error editing multiple orders: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}
	}

	@GetMapping("/getAllOrders")
	public ResponseEntity<List<Orders>> getAllOrders() {
		try {
			List<Orders> ordersList = os.getAllOrders();
			return ResponseEntity.ok(ordersList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/getLast7DaysOrders")
	public ResponseEntity<List<Orders>> getLast7DaysOrders() {
		try {
			List<Orders> ordersList = os.getLast7DaysOrders();
			return ResponseEntity.ok(ordersList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/filterOrdersByDateRange")
	public ResponseEntity<List<Orders>> filterOrdersByDateRange(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		try {
			List<Orders> ordersList = os.filterOrdersByDateRange(startDate, endDate);
			return ResponseEntity.ok(ordersList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActive")
	public ResponseEntity<List<Orders>> getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActive(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		try {
			List<Orders> ordersList = os.getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActive(startDate,
					endDate);
			return ResponseEntity.ok(ordersList);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/getActiveOrders")
	public List<Orders> getOrdersWithActiveProducts() {
		return os.getOrdersWithActiveProducts();
	}

}
