package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.History;

public interface HistoryRepository  extends JpaRepository<History, Long>{
    
}
