package com.eCommerce.eCommerceApp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HomeController {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@GetMapping("/login")
	public String login() {
		return "authentified successfully";
	}

}
