package com.eCommerce.eCommerceApp.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.entity.SourceOrder;
import com.eCommerce.eCommerceApp.repository.SourceOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SourceOrderService {

    @Autowired
    SourceOrderRepository sor;

    public void addSource(SourceOrder so) {

        Calendar calendar = Calendar.getInstance();
        so.setCreationDate(calendar.getTime());
        so.setIsActive(true);
        sor.save(so);
    }

    public ResponseEntity<String> updateSource(@PathVariable Long sourceOrderId,
            @RequestBody Map<String, String> sourceOrderInfo) {
        // sor.save(so);

        try {
            SourceOrder existingSourceOrder = sor.findById(sourceOrderId).get();

            if (existingSourceOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Source Order not found");
            }
            existingSourceOrder.setName(sourceOrderInfo.get("name"));
            existingSourceOrder.setColor(sourceOrderInfo.get("color"));

            sor.save(existingSourceOrder);
            return ResponseEntity.ok(" Source Order updated successfully");

        } catch (Exception e) {
            String errorMessage = "Error updating Source Order: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

        }

    }

    public List<SourceOrder> getAllSources() {
        return sor.findAll();
    }

    public List<SourceOrder> getActiveSources() {
        return sor.findByIsActiveTrue();
      }

}
