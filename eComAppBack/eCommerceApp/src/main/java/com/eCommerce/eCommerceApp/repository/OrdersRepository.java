package com.eCommerce.eCommerceApp.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eCommerce.eCommerceApp.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    // Add a custom query method to get orders from the last 7 days
    @Query("SELECT o FROM Orders o WHERE o.creationDate >= :date")
    List<Orders> findOrdersFromLast7Days(@Param("date") Date date);

    // List<Orders> findByDateBetween(LocalDate start, LocalDate end);

    List<Orders> findByCreationDateBetween(Date start, Date end);

    List<Orders> findByProductIsActiveTrue();

    List<Orders> findByCreationDateBetweenAndProductIsActiveTrueAndDeliveryCompanyIsActiveTrue(Date start, Date end);

}
