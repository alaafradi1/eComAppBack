package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	
}
