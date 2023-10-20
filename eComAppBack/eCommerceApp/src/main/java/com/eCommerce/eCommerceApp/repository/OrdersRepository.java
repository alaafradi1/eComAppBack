package com.eCommerce.eCommerceApp.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
    
}
