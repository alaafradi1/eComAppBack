package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.CampaignCost;

public interface CampaignCostRepository extends JpaRepository<CampaignCost, Long>{
    
}
