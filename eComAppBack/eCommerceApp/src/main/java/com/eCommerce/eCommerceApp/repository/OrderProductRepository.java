package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.eCommerceApp.entity.OrderProduct;

public interface  OrderProductRepository  extends JpaRepository<OrderProduct, Long>{
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderProduct op WHERE op.id = ?1")
    void deleteOrderProductById(Long orderProductId);
}
