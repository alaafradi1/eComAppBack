package com.eCommerce.eCommerceApp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.repository.OrdersRepository;
import com.eCommerce.eCommerceApp.service.StatisticsService;

@RestController
@CrossOrigin("*")
public class StatisticsController {
    @Autowired
    StatisticsService ss ;

    @GetMapping("/getProductsStatistics")
	public ResponseEntity<List<Map<String, Object>> > getProductsStatistics(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		try {
			List<Map<String, Object>> result = ss.getProductsStatistics(startDate, endDate);
            return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

    @GetMapping("/getCompaniesStatistics")
	public ResponseEntity<List<Map<String, Object>> > getCompaniesStatistics(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		try {
			List<Map<String, Object>> result = ss.getCompaniesStatistics(startDate, endDate);
            return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


    // @GetMapping("/getProductsStatistics")
	// public ResponseEntity<List<Map<String, Object>> > getProductsStatistics() {
	// 	try {
	// 		List<Map<String, Object>> result = ss.getProductsStatistics();
    //         return ResponseEntity.ok(result);
	// 	} catch (Exception e) {
	// 		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	// 	}
	// }

    @GetMapping("/productStatistics")
    public List<Map<String, String>> getProductStatistics() {
        return ss.getProductStatistics();
    }
    
}
