package com.eCommerce.eCommerceApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.Client;
import com.eCommerce.eCommerceApp.entity.Orders;

public interface ClientRepository  extends JpaRepository<Client, Long>{
    // Client findByPhoneNumber();

}
