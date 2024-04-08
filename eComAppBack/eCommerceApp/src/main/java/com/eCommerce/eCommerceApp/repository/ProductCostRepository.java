package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.ProductCost;

public interface ProductCostRepository extends JpaRepository<ProductCost, Long>{
    
}
