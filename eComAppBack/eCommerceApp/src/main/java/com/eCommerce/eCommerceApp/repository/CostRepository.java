package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.Cost;

public interface CostRepository extends JpaRepository<Cost, Long>{
    
}
