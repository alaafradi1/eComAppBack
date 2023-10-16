package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerceApp.entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long>{

}
