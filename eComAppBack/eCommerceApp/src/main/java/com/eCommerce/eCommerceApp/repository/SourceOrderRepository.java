package com.eCommerce.eCommerceApp.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eCommerce.eCommerceApp.entity.SourceOrder;

public interface SourceOrderRepository extends JpaRepository<SourceOrder, Long> {
        List<SourceOrder> findByIsActiveTrue();

}
