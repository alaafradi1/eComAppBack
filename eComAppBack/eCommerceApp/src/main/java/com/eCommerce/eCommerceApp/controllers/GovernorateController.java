package com.eCommerce.eCommerceApp.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerceApp.entity.Governorate;

@RestController
@CrossOrigin("*")
public class GovernorateController {

     @GetMapping("/getGovernorats")
    public List<Governorate> getGovernorats() {
        return Arrays.asList(Governorate.values());
    }
    
}
