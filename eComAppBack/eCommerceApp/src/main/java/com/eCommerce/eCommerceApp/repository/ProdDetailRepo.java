package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eCommerce.eCommerceApp.entity.ProdDetail;

public interface ProdDetailRepo extends JpaRepository<ProdDetail, Long>{

}
