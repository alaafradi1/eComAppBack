package com.eCommerce.eCommerceApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Client;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.ClientRepository;
import com.eCommerce.eCommerceApp.repository.DeliveryCompanyRepository;
import com.eCommerce.eCommerceApp.repository.OrdersRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

import ch.qos.logback.classic.pattern.DateConverter;

@Service
public class OrdersService {
    @Autowired
    ProductRepository pr;
    @Autowired
    OrdersRepository or;
    @Autowired
    ClientRepository cr;
    @Autowired
    DeliveryCompanyRepository dcr;

    public void addOrder(Map<String, String> orderInfo) {
        Calendar calendar = Calendar.getInstance(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

        Orders order = new Orders();
        Product product = pr.findById(Long.parseLong(orderInfo.get("idProduct"))).get();
        order.setProduct(product);

        order.setNote(orderInfo.get("note"));
        order.setPrintState(orderInfo.get("printState"));
        order.setState(orderInfo.get("productState"));
        order.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice")));

        // order.setDeliveryCompany(orderInfo.get("deliveryCompany"));
        DeliveryCompany deliveryCompany = dcr.findById(Long.parseLong(orderInfo.get("deliveryCompanyId"))).get();
        order.setDeliveryCompany(deliveryCompany);

        if (orderInfo.get("Date") == null) {
             order.setCreationDate(calendar.getTime());
        } 
        else{
            try{
                Date date = dateFormat.parse(orderInfo.get("Date"));
                order.setCreationDate(date);
            }catch (ParseException e) {
            e.printStackTrace();
        }

        }
       
        Client client = new Client();
        client.setName(orderInfo.get("clientName"));
        client.setAddress(orderInfo.get("clientAdress"));
        client.setPhoneNumber(orderInfo.get("clientNumber"));
        client.setPhoneNumber2(orderInfo.get("clientNumber2"));
        client.setGovernorate(orderInfo.get("clientGovernorate"));
        client.setCreationDate(calendar.getTime());

        client.setOrder(order);
        order.setClient(client);

        cr.save(client);
        or.save(order);
    }

    public ResponseEntity<String> addMultipleOrders(List<Map<String, String>> ordersInfo) {
        for (Map<String, String> orderInfo : ordersInfo) {
            System.out.println(orderInfo);

            Map<String, String> orderMap = new HashMap<>();

            String productName = orderInfo.get("Produit");
            List<Product> products = pr.findByProductName(productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found for productName: " + productName);
            }
            orderMap.put("idProduct", products.get(0).getProductId().toString());

            String deliveryCompanyName = orderInfo.get("Livreur");
            List<DeliveryCompany> deliveryCompanies = dcr.findByName(deliveryCompanyName);
            if (deliveryCompanies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("DeliveryCompany not found for deliveryCompanyName: " + deliveryCompanyName);
            }
            orderMap.put("deliveryCompanyId", deliveryCompanies.get(0).getIdDC().toString());

            orderMap.put("note", orderInfo.get("Note") != null ? orderInfo.get("Note") : "");
            orderMap.put("orderPrice", orderInfo.get("Prix"));
            orderMap.put("printState", orderInfo.get("Imprime") != null ? orderInfo.get("Imprime") : "Non"); // if "" =
                                                                                                             // "No"
            orderMap.put("productState", orderInfo.get("Status") != null ? orderInfo.get("Status") : "En cours");
            orderMap.put("clientName", orderInfo.get("Nom") != null ? orderInfo.get("Nom") : "");
            orderMap.put("clientAdress", orderInfo.get("Adresse") != null ? orderInfo.get("Adresse") : "");
            orderMap.put("clientNumber", orderInfo.get("Tel") != null ? orderInfo.get("Tel") : "");
            orderMap.put("clientNumber2", orderInfo.get("Tel 2") != null ? orderInfo.get("Tel 2") : "");
            orderMap.put("clientGovernorate", orderInfo.get("Gouvernorat") != null ? orderInfo.get("Gouvernorat") : "");

            // date : transfroming the string into date format 
            orderMap.put("Date", convertExcelSerialDateToDateFormat(orderInfo.get("Date")));

            // end date 
            this.addOrder(orderMap);
        }

        return ResponseEntity.ok("Orders added successfully");
    }

    public static String convertExcelSerialDateToDateFormat(String excelSerialDate) {
        double serialDateValue = Double.parseDouble(excelSerialDate);

        // Convert serial date to milliseconds since January 1, 1900
        long excelTime = (long) ((serialDateValue - 25569) * 24 * 3600 * 1000);

        // Convert Excel time to Java Date
        Date date = new Date(excelTime);

        // Format the date as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        return dateFormat.format(date);
    }


    public List<Orders> getAllOrders() {
        // List<Orders> ordersList = or.findAll();
        return (or.findAll());
    }

    public List<Orders> getLast7DaysOrders() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        Date sevenDaysAgo = calendar.getTime();
        return or.findOrdersFromLast7Days(sevenDaysAgo);

        // return( or.findAll());
    }

    public List<Orders> filterOrdersByDateRange(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date start = null;
        Date end = null;
        try {
            start = dateFormat.parse(startDate);
            end = dateFormat.parse(endDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Adjust the start date to be one day before
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        start = calendar.getTime();

        // Adjust the end date to be one day after
        calendar.setTime(end);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        end = calendar.getTime();

        return or.findByCreationDateBetween(start, end);
    }

    public List<Orders> getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActive(String startDate,
            String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date start = null;
        Date end = null;
        try {
            start = dateFormat.parse(startDate);
            end = dateFormat.parse(endDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Adjust the start date to be one day before
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        start = calendar.getTime();

        // Adjust the end date to be one day after
        calendar.setTime(end);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        end = calendar.getTime();

        return or.findByCreationDateBetweenAndProductIsActiveTrueAndDeliveryCompanyIsActiveTrue(start, end);
    }

    public void editOrder(Map<String, String> orderInfo) {
        Orders existingOrder = or.findById(Long.parseLong(orderInfo.get("idOrder"))).get();
        existingOrder.setNote(orderInfo.get("note"));
        existingOrder.setPrintState(orderInfo.get("printState"));
        existingOrder.setState(orderInfo.get("productState"));
        existingOrder.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice")));

        Product product = pr.findById(Long.parseLong(orderInfo.get("idProduct"))).get();
        existingOrder.setProduct(product);

        Client existingClient = cr.findById(Long.parseLong(orderInfo.get("idClient"))).get();
        existingClient.setName(orderInfo.get("clientName"));
        existingClient.setAddress(orderInfo.get("clientAdress"));
        existingClient.setPhoneNumber(orderInfo.get("clientNumber"));
        existingClient.setPhoneNumber2(orderInfo.get("clientNumber2"));
        existingClient.setGovernorate(orderInfo.get("clientGovernorate"));
        cr.save(existingClient);
        existingOrder.setClient(existingClient);

        DeliveryCompany deliveryCompany = dcr.findById(Long.parseLong(orderInfo.get("deliveryCompanyId"))).get();
        existingOrder.setDeliveryCompany(deliveryCompany);

        or.save(existingOrder);

    }

    public List<Orders> getOrdersWithActiveProducts() {
        return or.findByProductIsActiveTrue();
    }

    public void editMultipleOrders(JsonNode orderInfo) {
        JsonNode orderIDs = orderInfo.get("orderIDs");

        if (orderIDs != null && orderIDs.isArray()) {
            for (JsonNode orderID : orderIDs) {
                Orders existingOrder = or.findById(orderID.asLong()).get();
                if (orderInfo.get("note").asText() != "") {
                    existingOrder.setNote(orderInfo.get("note").asText());
                }
                if (orderInfo.get("orderPrice").asText() != "") {
                    existingOrder.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice").asText()));
                }
                if (orderInfo.get("printState").asText() != "") {
                    existingOrder.setPrintState(orderInfo.get("printState").asText());
                }
                if (orderInfo.get("productState").asText() != "") {
                    existingOrder.setState(orderInfo.get("productState").asText());
                }
                if (orderInfo.get("idProduct").asInt() != 0) {
                    Product newProduct = pr.findById(orderInfo.get("idProduct").asLong()).get();
                    existingOrder.setProduct(newProduct);
                }
                if (orderInfo.get("clientAdress").asText() != "" || orderInfo.get("clientGovernorate").asText() != "") {
                    Client existingClient = existingOrder.getClient();
                    if (orderInfo.get("clientAdress").asText() != "") {
                        existingClient.setAddress(orderInfo.get("clientAdress").asText());
                    }
                    if (orderInfo.get("clientGovernorate").asText() != "") {
                        existingClient.setGovernorate(orderInfo.get("clientGovernorate").asText());
                    }

                }
                if (orderInfo.get("deliveryCompanyId").asLong() != 0) {
                    DeliveryCompany newDeliveryCompany = dcr.findById(orderInfo.get("deliveryCompanyId").asLong())
                            .get();
                    existingOrder.setDeliveryCompany(newDeliveryCompany);
                }

                or.save(existingOrder);

            }
        }

    }

    public void deleteOrdre(Long idOrdre) {
        or.deleteById(idOrdre);
    }

}
