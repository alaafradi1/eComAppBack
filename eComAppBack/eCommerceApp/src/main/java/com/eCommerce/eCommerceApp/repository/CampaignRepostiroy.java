package com.eCommerce.eCommerceApp.repository;

import com.eCommerce.eCommerceApp.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepostiroy extends JpaRepository<Campaign, Long>{
    List<Campaign> findByNewCostIdIsNotNull();
    List<Campaign> findByNewIsActiveIsNotNull();

}
