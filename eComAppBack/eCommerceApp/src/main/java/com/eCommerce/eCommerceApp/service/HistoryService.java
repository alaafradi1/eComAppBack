package com.eCommerce.eCommerceApp.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.History;
import com.eCommerce.eCommerceApp.repository.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    HistoryRepository hr;

    public void addCaisseHistory(Caisse c) {      
        History history = new History();
        history.setType("Caisse");
        String description = "A "+c.getType() +" of "+c.getAmount()+"DT was ADDED at "+c.getCreationDate();
        history.setDescription(description);
		history.setCreationDate(c.getCreationDate());
        hr.save(history);
    }

    public void deleteCaisseHistory(Caisse c, String reason) {      
        History history = new History();
        history.setType("Caisse");
        String description = "A "+c.getType() +" of "+c.getAmount()+"DT was DELETED at "+c.getCreationDate();
        history.setDescription(description);
		history.setCreationDate(c.getCreationDate());
        history.setReason(reason);
        hr.save(history);
    }

}
