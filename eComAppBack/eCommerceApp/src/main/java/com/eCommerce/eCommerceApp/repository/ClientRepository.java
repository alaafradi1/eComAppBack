package com.eCommerce.eCommerceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.Client;

public interface ClientRepository  extends JpaRepository<Client, Long>{
    
}
