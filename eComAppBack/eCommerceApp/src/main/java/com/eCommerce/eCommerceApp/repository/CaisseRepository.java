package com.eCommerce.eCommerceApp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eCommerce.eCommerceApp.entity.Caisse;

public interface CaisseRepository extends JpaRepository<Caisse, Long>{

    List<Caisse> findByCreationDateBetween(Date start, Date end);
	
     @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Revenu'")
    Double getSumOfAllRevenuAmount();

    @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Depense'")
    Double getSumOfAllDepenseAmount();

    @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Revenu' AND c.product IS NULL")
    Double getSumOfRevenuAmountWithNullProductId();

    @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Depense' AND c.product IS NULL")
    Double getSumOfDepenseAmountWithNullProductId();

    @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Revenu' AND c.product.productId = :productId")
    Double getSumOfRevenuAmountWithProductId(@Param("productId") Long productId);

    @Query("SELECT SUM(c.amount) FROM Caisse c WHERE c.type = 'Depense' AND c.product.productId = :productId")
    Double getSumOfDepenseAmountWithProductId(@Param("productId") Long productId);
}
