package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.CompaignCost;

public interface CompaignCostRepository  extends JpaRepository<CompaignCost, Long>{
    
}
