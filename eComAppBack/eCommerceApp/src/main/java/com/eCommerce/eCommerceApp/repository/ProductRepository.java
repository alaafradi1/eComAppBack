package com.eCommerce.eCommerceApp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
