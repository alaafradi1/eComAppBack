package com.eCommerce.eCommerceApp.service;

import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Client;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.ClientRepository;
import com.eCommerce.eCommerceApp.repository.OrdersRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;

@Service
public class OrdersService {
    @Autowired
	ProductRepository pr;
    @Autowired
	OrdersRepository or;
    @Autowired
	ClientRepository cr;

    public void addOrder(Map<String, String> orderInfo) {
        Orders order = new Orders();
        Product product =  pr.findById(Long.parseLong(orderInfo.get("idProduct"))).get();
        order.setProduct(product);

        order.setNote(orderInfo.get("note"));
        order.setPrintState(orderInfo.get("printState"));
        order.setState(orderInfo.get("productState"));
        order.setDeliveryCompany(orderInfo.get("deliveryCompany"));
        Calendar calendar = Calendar.getInstance();
		order.setCreationDate(calendar.getTime());

        Client client = new Client();
        client.setName(orderInfo.get("clientName"));
        client.setAddress(orderInfo.get("clientAdress"));
        client.setPhoneNumber(orderInfo.get("clientNumber"));
        client.setGovernorate(orderInfo.get("clientGovernorate"));
        client.setCreationDate(calendar.getTime());

        client.setOrder(order);
        order.setClient(client);

        cr.save(client);
		or.save(order);
	}

    
}
