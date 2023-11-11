package com.eCommerce.eCommerceApp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByIsActiveTrue();
    List<Product> findByProductName(String productName);

}
