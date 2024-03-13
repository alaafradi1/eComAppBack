package com.eCommerce.eCommerceApp.service;

import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Governorate;

@Service
public class GovernoratService {
    public boolean doesGouvernoratExist(String gouvernoratName) {
        try {
            Governorate.valueOf(gouvernoratName);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
