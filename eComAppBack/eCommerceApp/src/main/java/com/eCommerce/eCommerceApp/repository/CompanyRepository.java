package com.eCommerce.eCommerceApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    List<Company> findByIsActiveTrue();
	
}
