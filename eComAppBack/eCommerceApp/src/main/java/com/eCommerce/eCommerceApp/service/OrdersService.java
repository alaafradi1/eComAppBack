package com.eCommerce.eCommerceApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.eCommerceApp.entity.Client;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Cost;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.entity.Governorate;
import com.eCommerce.eCommerceApp.entity.OrderProduct;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.ClientRepository;
import com.eCommerce.eCommerceApp.repository.CostRepository;
import com.eCommerce.eCommerceApp.repository.DeliveryCompanyRepository;
import com.eCommerce.eCommerceApp.repository.OrderProductRepository;
import com.eCommerce.eCommerceApp.repository.OrdersRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.pattern.DateConverter;
import ch.qos.logback.core.boolex.Matcher;

@Service
public class OrdersService {
    @Autowired
    ProductRepository pr;
    @Autowired
    OrdersRepository or;
    @Autowired
    ClientRepository cr;
    @Autowired
    OrderProductRepository opr;
    @Autowired
    DeliveryCompanyRepository dcr;
    @Autowired
    GovernoratService govService;
    @Autowired
    CostRepository costRep;

    public void addOrderWithMupltipleProducts(String orderInfoString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode orderInfo = objectMapper.readTree(orderInfoString);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

            /*** CLIENT ***/
            Client client = new Client();
            client.setName(orderInfo.get("clientName").asText());
            client.setAddress(orderInfo.get("clientAdress").asText());
            client.setPhoneNumber(orderInfo.get("clientNumber").asText());
            client.setPhoneNumber2(orderInfo.get("clientNumber2").asText());
            client.setGovernorate(orderInfo.get("clientGovernorate").asText());
            client.setCreationDate(calendar.getTime());
            cr.save(client);

            /*** ORDER ***/
            Orders order = new Orders();
            order.setClient(client); // client with no order
            order.setNote(orderInfo.get("note").asText());
            order.setSource(orderInfo.get("source").asText());
            order.setPrintState("");
            order.setState(orderInfo.get("productState").asText());
            order.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice").asText()));
            order.setIsPrinted(Boolean.parseBoolean(orderInfo.get("isPrinted").asText()));
            order.setIsExported(Boolean.parseBoolean(orderInfo.get("isExported").asText()));
            order.setIsSaved(Boolean.parseBoolean(orderInfo.get("isSaved").asText()));

            /*** DELIVERY_COMPANY ***/
            DeliveryCompany deliveryCompany = dcr.findById(Long.parseLong(orderInfo.get("deliveryCompanyId").asText()))
                    .get();
            order.setDeliveryCompany(deliveryCompany);

            /*** CREATION_DATE ***/
            if (orderInfo.get("Date") == null) {
                order.setCreationDate(calendar.getTime());
            } else {
                try {
                    Date date = dateFormat.parse(orderInfo.get("Date").asText());
                    order.setCreationDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            /*** ORDER_PRODUCTS ***/
            JsonNode orderProductsNode = orderInfo.get("orderProducts");
            List<OrderProduct> orderProducts = new ArrayList<>();
            order.setOrderProducts(new ArrayList<>());
            // List<OrderProduct> orderProducts =
            // objectMapper.convertValue(orderProductsNode, new
            // TypeReference<List<OrderProduct>>() {});
            for (JsonNode productInOrder : orderProductsNode) {
                // Convert each element to an OrderProduct object
                OrderProduct orderProduct = new OrderProduct();
                Product product = pr.findById(Long.parseLong(productInOrder.get("productId").asText())).get();
                orderProduct.setProduct(product);
                orderProduct.setQuantity(Integer.parseInt(productInOrder.get("selectedQuantity").asText()));
                orderProduct.setOrder(order);

                Long currentCostId = product.getCurrentCostId();
                orderProduct.setProductCostId(currentCostId);

                Cost currentCost = costRep.findById(currentCostId).get();
                Float currentCostAmount = currentCost.getCostAmount();
                orderProduct.setProductCostAmount(currentCostAmount);

                // order.addOrderProduct(orderProduct);
                // opr.save(orderProduct);
                orderProducts.add(orderProduct);
            }
            order.setOrderProducts(orderProducts);

            /*** ORDER AND PRODUCT ***/
            or.save(order); // with client who doens't have order
            client.setOrder(order); // setting the order to the client
            cr.save(client);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editOrderWithMupltipleProducts(String orderInfoString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode orderInfo = objectMapper.readTree(orderInfoString);

            Orders existingOrder = or.findById(Long.parseLong(orderInfo.get("idOrder").asText())).get();

            /*** CLIENT ***/
            Client existingClient = cr.findById(Long.parseLong(orderInfo.get("idClient").asText())).get();
            existingClient.setName(orderInfo.get("clientName").asText());
            existingClient.setAddress(orderInfo.get("clientAdress").asText());
            existingClient.setPhoneNumber(orderInfo.get("clientNumber").asText());
            existingClient.setPhoneNumber2(orderInfo.get("clientNumber2").asText());
            existingClient.setGovernorate(orderInfo.get("clientGovernorate").asText());
            cr.save(existingClient);

            /*** ORDER ***/
            existingOrder.setClient(existingClient);
            existingOrder.setNote(orderInfo.get("note").asText());
            existingOrder.setSource(orderInfo.get("source").asText());
            existingOrder.setPrintState("");
            existingOrder.setState(orderInfo.get("productState").asText());
            existingOrder.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice").asText()));

            existingOrder.setIsPrinted(Boolean.parseBoolean(orderInfo.get("isPrinted").asText()));
            existingOrder.setIsExported(Boolean.parseBoolean(orderInfo.get("isExported").asText()));
            existingOrder.setIsSaved(Boolean.parseBoolean(orderInfo.get("isSaved").asText()));

            /*** DELIVERY_COMPANY ***/
            DeliveryCompany deliveryCompany = dcr.findById(Long.parseLong(orderInfo.get("deliveryCompanyId").asText()))
                    .get();
            existingOrder.setDeliveryCompany(deliveryCompany);

            /*** ORDER_PRODUCTS ***/
            /** deleting the old orderProducts **/
            List<OrderProduct> orderProductsToBeDeleted = or.findById(Long.parseLong(orderInfo.get("idOrder").asText()))
                    .get().getOrderProducts();
            deleteOrderProductsByOrder(orderProductsToBeDeleted); // deleting the orderProducts using this function
                                                                  // because delete won't work
            existingOrder.getOrderProducts().clear(); // delete the existing orderProduct list

            /** adding the new orderProducts **/
            JsonNode orderProductsNode = orderInfo.get("orderProducts");
            List<OrderProduct> orderProducts = new ArrayList<>();
            existingOrder.setOrderProducts(new ArrayList<>());

            for (JsonNode productInOrder : orderProductsNode) {
                // Convert each element to an OrderProduct object
                OrderProduct orderProduct = new OrderProduct();
                Product product = pr.findById(Long.parseLong(productInOrder.get("productId").asText())).get();
                orderProduct.setProduct(product);
                orderProduct.setQuantity(Integer.parseInt(productInOrder.get("selectedQuantity").asText()));
                orderProduct.setOrder(existingOrder);

                Long currentCostId = product.getCurrentCostId();
                orderProduct.setProductCostId(currentCostId);

                Cost currentCost = costRep.findById(currentCostId).get();
                Float currentCostAmount = currentCost.getCostAmount();
                orderProduct.setProductCostAmount(currentCostAmount);

                orderProducts.add(orderProduct);
            }

            existingOrder.setOrderProducts(orderProducts);

            /*** ORDER AND PRODUCT ***/
            or.save(existingOrder);
            existingClient.setOrder(existingOrder);
            cr.save(existingClient);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteOrderProductsByOrder(List<OrderProduct> orderProducts) {
        // Manually delete all OrderProduct instances
        for (OrderProduct orderProduct : orderProducts) {
            opr.deleteOrderProductById(orderProduct.getId()); // using a new function created in the repository because
                                                              // the default "delete" funciton won't work
        }
    }

    public List<Orders> getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts(
            String startDate,
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

        boolean deliveryCompanyIsActive = true; // Set as needed
        boolean productIsActive = true; // Set as needed

        return or
                .findDistinctByCreationDateBetweenAndDeliveryCompany_IsActiveAndOrderProducts_Product_IsActiveOrderByCreationDate(
                        start, end, deliveryCompanyIsActive, productIsActive);
    }

    public void editStatusOfOrders(JsonNode orderInfo) {
        JsonNode orderIDs = orderInfo.get("orderIDs");
        if (orderIDs != null && orderIDs.isArray()) {
            for (JsonNode orderID : orderIDs) {
                Orders existingOrder = or.findById(orderID.asLong()).get();
                if (orderInfo.get("isPrinted").asText() != "") {
                    existingOrder.setIsPrinted(Boolean.parseBoolean(orderInfo.get("isPrinted").asText()));
                }
                if (orderInfo.get("isSaved").asText() != "") {
                    existingOrder.setIsSaved(Boolean.parseBoolean(orderInfo.get("isSaved").asText()));
                }
                if (orderInfo.get("isExported").asText() != "") {
                    existingOrder.setIsExported(Boolean.parseBoolean(orderInfo.get("isExported").asText()));
                }

                or.save(existingOrder);

            }
        }
    }

    public void editMultipleOrderswithMultipleProducts(JsonNode orderInfo) {
        JsonNode orderIDs = orderInfo.get("orderIDs");

        if (orderIDs != null && orderIDs.isArray()) {
            for (JsonNode orderID : orderIDs) {
                Orders existingOrder = or.findById(orderID.asLong()).get();
                if (orderInfo.get("note").asText() != "") {
                    existingOrder.setNote(orderInfo.get("note").asText());
                }
                // if (orderInfo.get("orderPrice").asText() != "") {
                // existingOrder.setOrderPrice(Float.parseFloat(orderInfo.get("orderPrice").asText()));
                // }
                if (orderInfo.get("printState").asText() != "") {
                    existingOrder.setPrintState("");
                }
                if (orderInfo.get("productState").asText() != "") {
                    existingOrder.setState(orderInfo.get("productState").asText());
                }
                if (orderInfo.get("source").asText() != "") {
                    existingOrder.setSource(orderInfo.get("source").asText());
                }
                if (orderInfo.get("isPrinted").asText() != "") {
                    existingOrder.setIsPrinted(Boolean.parseBoolean(orderInfo.get("isPrinted").asText()));
                }
                if (orderInfo.get("isExported").asText() != "") {
                    existingOrder.setIsExported(Boolean.parseBoolean(orderInfo.get("isExported").asText()));
                }
                if (orderInfo.get("isSaved").asText() != "") {
                    existingOrder.setIsSaved(Boolean.parseBoolean(orderInfo.get("isSaved").asText()));
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

    public ResponseEntity<String> addMultipleOrders(List<Map<String, String>> ordersInfo) {
        List<String> ordersDetails = new ArrayList<>();
        for (Map<String, String> orderInfo : ordersInfo) {
            String orderReference = orderInfo.get("Reference");  
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> orderMap = new HashMap<>();

                /*** ORDER PRODUCTS ***/
                String productsColumns = orderInfo.get("Produits");
                String[] productsStringList = productsColumns.split(", ");
                List<Map<String, String>> orderProductsList = new ArrayList<>();

                for (String productsString : productsStringList) {
                    // Use a regular expression to extract quantity and productName
                    // Pattern pattern = Pattern.compile("\\((\\d+)\\) (.+)");
                    Pattern pattern = Pattern.compile("\\((\\d+)\\) (.+?) \\((\\d+)\\) \\((\\d+)\\)"); // contains the
                                                                                                       // cost
                    java.util.regex.Matcher matcher = pattern.matcher(productsString);

                    if (matcher.matches()) {
                        String quantity = matcher.group(1);
                        String productName = matcher.group(2);
                        // String productPrice = matcher.group(3);
                        // String productCost = matcher.group(4);

                        // add condition when product not found
                        if (pr.findByProductName(productName).isEmpty()) {
                            return ResponseEntity.ok("produit <" + productName + "> introuvable");
                        } else {
                            Long productId = pr.findByProductName(productName).get(0).getProductId();
                            Map<String, String> orderProductMap = new HashMap<>();
                            orderProductMap.put("productId", productId.toString());
                            orderProductMap.put("selectedQuantity", quantity);
                            // Add the Map to the List
                            orderProductsList.add(orderProductMap);
                        }

                    } else {
                        // Handle invalid input
                        // System.err.println("Format d'entrée du produit non valide : " + productsString);
                        return ResponseEntity.ok("Format d'entrée du produit non valide : " + productsString);
            
                    }
                }
                orderMap.put("orderProducts", orderProductsList);

                /*** DELIVERY COMPANY ***/
                String deliveryCompanyName = orderInfo.get("Livreur");
                List<DeliveryCompany> deliveryCompanies = dcr.findByName(deliveryCompanyName);
                if (deliveryCompanies.isEmpty()) {
                   return ResponseEntity.ok("Société de livraison <" + deliveryCompanyName + "> introuvable");
                }
                else{
                     orderMap.put("deliveryCompanyId", deliveryCompanies.get(0).getIdDC().toString());
                }
               
                /*** ORDER ***/
                orderMap.put("note", orderInfo.get("Note") != null ? orderInfo.get("Note") : "");
                orderMap.put("orderPrice", orderInfo.get("Prix"));
                orderMap.put("printState", orderInfo.get("Imprime") != null ? orderInfo.get("Imprime") : "Non"); // if
                                                                                                                 // "" =
                orderMap.put("source", orderInfo.get("Source"));
                orderMap.put("productState", orderInfo.get("Statut") != null ? orderInfo.get("Statut") : "En cours");
                orderMap.put("clientName", orderInfo.get("Nom") != null ? orderInfo.get("Nom") : "");
                orderMap.put("clientAdress", orderInfo.get("Adresse") != null ? orderInfo.get("Adresse") : "");
                orderMap.put("clientNumber", orderInfo.get("Tel") != null ? orderInfo.get("Tel") : "");
                orderMap.put("clientNumber2", orderInfo.get("Tel 2") != null ? orderInfo.get("Tel 2") : "");
                
                orderMap.put("isPrinted", orderInfo.get("isPrinted") != null ? orderInfo.get("isPrinted") : false);
                orderMap.put("isExported", orderInfo.get("isExported") != null ? orderInfo.get("isExported") : false);
                orderMap.put("isSaved", orderInfo.get("isSaved") != null ? orderInfo.get("isSaved") : false);

                /*** DATE : transfroming the string into date format ***/
                orderMap.put("Date", convertExcelSerialDateToDateFormat(orderInfo.get("Date")));

                /*** Governorate ***/
                String givenGovernoratName = orderInfo.get("Gouvernorat");
                if(govService.doesGouvernoratExist(givenGovernoratName)){
                    orderMap.put("clientGovernorate", orderInfo.get("Gouvernorat"));
                }else{
                    return ResponseEntity.ok("Governorat <"+ givenGovernoratName + "> avec la reference <" + orderReference + "> introuvable");
                }

                try { 
                    // need to be a list so that if on row has a problem, no order will be created.
                    String orderDetail = objectMapper.writeValueAsString(orderMap);
                    ordersDetails.add(orderDetail);
                    // this.addOrderWithMupltipleProducts(orderDetail);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                   ResponseEntity.ok("problème d'ajout de l'ordre acen la reference :  " + orderReference);
                }

            } catch (Error e) {                
                return ResponseEntity.ok("problème dans la rangée :  " + orderReference);
            }

        }

        for(String orderDetail : ordersDetails){
            this.addOrderWithMupltipleProducts(orderDetail);
        }

        return ResponseEntity.ok("success");
    }

    public static String convertExcelSerialDateToDateFormat(String excelSerialDate) {
        // double serialDateValue = Double.parseDouble(excelSerialDate);
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = inputFormat.parse(excelSerialDate);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteOrdre(Long idOrdre) {
        or.deleteById(idOrdre);
    }

}
