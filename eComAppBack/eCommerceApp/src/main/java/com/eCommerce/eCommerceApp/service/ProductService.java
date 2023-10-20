package com.eCommerce.eCommerceApp.service;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.CompanyRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
	ProductRepository pr;
    @Autowired
    CompanyRepository cr;

    public void addProduct(Map<String, String> productWithCompany) {
        Product product = new Product();
        Company company =  cr.findById(Long.parseLong(productWithCompany.get("idCompany"))).get();
        product.setCompany(company);
       // product.setProductAddCost(Float.parseFloat(productWithCompany.get("productAddCost")));
        product.setProductCost(Float.parseFloat(productWithCompany.get("productCost")));
        product.setProductName(productWithCompany.get("productName"));
        product.setProductPrice(Float.parseFloat(productWithCompany.get("productPrice")));
        Calendar calendar = Calendar.getInstance();
		product.setCreationDate(calendar.getTime());
		pr.save(product);
	}


    
}
