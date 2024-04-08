package com.eCommerce.eCommerceApp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long>{
    List<DeliveryCompany> findByIsActiveTrue();
    List<DeliveryCompany> findByName(String deliveryCompanyName);
}
