package com.eCommerce.eCommerceApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.OrderProduct;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.CompanyRepository;
import com.eCommerce.eCommerceApp.repository.OrdersRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;

@Service
public class StatisticsService {

    @Autowired
    OrdersRepository or;
    @Autowired
    OrdersService os;
    @Autowired
    ProductRepository pr;
    @Autowired
    ProductService ps;
    @Autowired
    CompanyRepository companyRep;
    @Autowired
    CompanyService companySer;
    @Autowired
    CaisseRepository cr;

    public List<Map<String, Object>> getProductsStatistics(String startDate, String endDate) {

        List<Map<String, Object>> productsStatistics = new ArrayList<>();

        // the list of orders need to be filtered by dateRange // active product and Ent
        List<Orders> orders = os.getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts(
                startDate, endDate);

        // getting all the active products because all of them need to be shown in the
        // table even if they have no orders
        List<Product> products = ps.getActiveProducts();

        // getting the stat of orders for each product
        for (Product product : products) {
            String productName = product.getProductName();

            // getting the list of orders for a specific product
            List<Orders> ordersForProduct = orders.stream().filter(order -> order.getOrderProducts().stream()
                    .anyMatch(productOrder -> productOrder.getProduct().equals(product)))
                    .collect(Collectors.toList());

            // total Orders
            String totalOrders = String.valueOf(ordersForProduct.size());

            // total nbr of products in the total orders ( 3 orders can have 6 products of
            // the P1)
            double totalProductsInOrders = ordersForProduct.stream().flatMap(order -> order.getOrderProducts().stream())
                    .filter(orderProduct -> orderProduct.getProduct().equals(product))
                    .mapToDouble(productOrder -> productOrder.getQuantity())
                    .sum();

            // total Orders for each state
            Map<String, Long> ordersStateCounts = ordersForProduct.stream()
                    .collect(Collectors.groupingBy(Orders::getState, Collectors.counting()));

            //salesInfoOfProduct 
            double[] salesInfoOfProduct = salesInfoOfProduct(product, ordersForProduct);
            double totalProductsInDeliveredOrders = salesInfoOfProduct[0] ;
            double estimatedRevenuFromSales = salesInfoOfProduct[1] ;

            // real Revenu && Depense From Caisse
            double realRevenuFromCaisse = getCaisseAmountByTypeforProduct(product, "Revenu");
            double realDepenseFromCaisse = getCaisseAmountByTypeforProduct(product, "Depense");

            // collecting the stats in one json object
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("name", productName);
            productMap.put("totalOrders", totalOrders);
            productMap.put("totalProductsInOrders", String.valueOf(totalProductsInOrders));
            productMap.put("ordersStateCounts", ordersStateCounts);
            productMap.put("totalProductsInDeliveredOrders", String.valueOf(totalProductsInDeliveredOrders));
            productMap.put("estimatedRevenuFromSales", String.valueOf(estimatedRevenuFromSales));
            productMap.put("realRevenuFromCaisse", String.valueOf(realRevenuFromCaisse));
            productMap.put("realDepenseFromCaisse", String.valueOf(realDepenseFromCaisse));

            // collecting the orders stats in one object
            productsStatistics.add(productMap);
        }
        return productsStatistics;
    }

    public double[] salesInfoOfProduct(Product product,List<Orders> ordersForProduct){
        double totalProductsInDeliveredOrders = totalProductsInOrdersByStateForProduct(ordersForProduct, product, "Livre");
        float productPrice = product.getProductPrice();
        double estimatedRevenuFromSales = totalProductsInDeliveredOrders * productPrice;

        return new double[]{totalProductsInDeliveredOrders, estimatedRevenuFromSales};
    }

    public double totalProductsInOrdersByStateForProduct(List<Orders> ordersForProduct,Product product, String state) {
        double totalProductsInOrdersByState = ordersForProduct.stream()
                    .filter(order -> order.getState().equals(state))
                    .flatMap(order -> order.getOrderProducts().stream())
                    .filter(orderProduct -> orderProduct.getProduct().equals(product))
                    .mapToDouble(productOrder -> productOrder.getQuantity())
                    .sum();
        return totalProductsInOrdersByState;
    }

    public double getCaisseAmountByTypeforProduct(Product product, String type) {
        List<Caisse> caisseOfProduct = cr.findAll().stream()
                .filter(caisse -> (caisse.getProduct() == null && product == null) ||
                        (caisse.getProduct() != null &&
                                caisse.getProduct().getProductId() != null &&
                                caisse.getProduct().getProductId().equals(product.getProductId())))
                .collect(Collectors.toList());

        double amount = caisseOfProduct.stream().filter(caisse -> type.equals(caisse.getType()))
                .mapToDouble(Caisse::getAmount).sum();

        return amount;
    }

    public List<Map<String, Object>> getCompaniesStatistics(String startDate, String endDate) {

        List<Map<String, Object>> companiesStatistics = new ArrayList<>();

        // the list of orders need to be filtered by dateRange // active product and Ent
        List<Orders> orders = os.getOrdersByCreationDateBetweenAndProductAndDeliveryCompanyIsActiveAndMultipleProducts(
                startDate, endDate);

        // getting all the active products because all of them need to be shown in the
        // table even if they have no orders
        List<Company> companies = companySer.getActiveCompanies();

        // getting the stat of orders for each product
        for (Company company : companies) {
            String companyName = company.getCompanyName();

            // getting the list of orders for a specific product
            List<Orders> ordersForCompany = orders.stream().filter(order -> order.getOrderProducts().stream()
                    .anyMatch(productOrder -> productOrder.getProduct().getCompany().equals(company)))
                    .collect(Collectors.toList());

            // total Orders
            String totalOrders = String.valueOf(ordersForCompany.size());

            // total nbr of products in the total orders ( 3 orders can have 6 products of
            // the P1)
            double totalProductsInOrders = ordersForCompany.stream().flatMap(order -> order.getOrderProducts().stream())
                    .filter(orderProduct -> orderProduct.getProduct().getCompany().equals(company))
                    .mapToDouble(productOrder -> productOrder.getQuantity())
                    .sum();

            // total Orders for each state
            Map<String, Long> ordersStateCounts = ordersForCompany.stream()
                    .collect(Collectors.groupingBy(Orders::getState, Collectors.counting()));

            // totalProductsInDeliveredOrders
            double totalProductsInDeliveredOrders = totalProductsInOrdersByStateForCompany(ordersForCompany, company,"Livre");

            // estimated Earnings
            List<Product> productsOfCompany = company.getProducts();
            double estimatedRevenuFromSales = 0;
            for (Product product : productsOfCompany) {
                // getting the list of orders for a specific product
                List<Orders> ordersForProduct = orders.stream().filter(order -> order.getOrderProducts().stream()
                        .anyMatch(productOrder -> productOrder.getProduct().equals(product)))
                        .collect(Collectors.toList());

                double totalProductsInDeliveredOrdersInCompany = totalProductsInOrdersByStateForProduct(ordersForProduct, product, "Livre");
            // estimated Earnings
                float productPrice = product.getProductPrice();
                estimatedRevenuFromSales += totalProductsInDeliveredOrdersInCompany * productPrice;
            }

            // real Revenu && Depense From Caisse
            double realRevenuFromCaisse = getCaisseAmountByTypeforCompany(company,"Revenu");
            double realDepenseFromCaisse = getCaisseAmountByTypeforCompany(company,"Depense");

            // collecting the stats in one json object
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("name", companyName);
            productMap.put("totalOrders", totalOrders);
            productMap.put("totalProductsInOrders", String.valueOf(totalProductsInOrders));
            productMap.put("ordersStateCounts", ordersStateCounts);
            productMap.put("totalProductsInDeliveredOrders", String.valueOf(totalProductsInDeliveredOrders));
            productMap.put("estimatedRevenuFromSales", String.valueOf(estimatedRevenuFromSales));
            productMap.put("realRevenuFromCaisse", String.valueOf(realRevenuFromCaisse));
            productMap.put("realDepenseFromCaisse", String.valueOf(realDepenseFromCaisse));

            // collecting the orders stats in one object
            companiesStatistics.add(productMap);
        }
        return companiesStatistics;
    }

    public double totalProductsInOrdersByStateForCompany(List<Orders> ordersForCompany,Company company, String state) {
        double totalProductsInOrdersByState = ordersForCompany.stream()
                    .filter(order -> order.getState().equals(state))
                    .flatMap(order -> order.getOrderProducts().stream())
                    .filter(orderProduct -> orderProduct.getProduct().getCompany().equals(company))
                    .mapToDouble(productOrder -> productOrder.getQuantity())
                    .sum();
        return totalProductsInOrdersByState;
    }
    public double getCaisseAmountByTypeforCompany(Company company, String type){
        List<Caisse> productCaisseRevenu = cr.findAll().stream()
                    .filter(caisse -> 
                                (caisse.getProduct() == null && company == null) ||
                                (   
                                    caisse.getProduct() != null &&
                                    caisse.getProduct().getProductId() != null &&
                                    caisse.getProduct().getCompany().equals(company)
                                )
                                && type.equals(caisse.getType())
                            ).collect(Collectors.toList());

        double amount = productCaisseRevenu.stream().mapToDouble(Caisse::getAmount).sum();

        return amount;
   }

    public Map<String, String> getOrdersStatistics() {

        long totalOrders = getTotalNumberOfOrders();

        Map<String, String> orderStatistics = new HashMap<>();
        orderStatistics.put("totalOrders", String.valueOf(totalOrders));

        return orderStatistics;
    }

    public long getTotalNumberOfOrders() {
        return or.count();
    }

    public List<Map<String, String>> getProductStatistics() {
        List<Object[]> result = or.getProductStatistics();
        return result.stream()
                .map(row -> {
                    Map<String, String> productStatistics = new HashMap<>();
                    productStatistics.put("productName", (String) row[0]);
                    productStatistics.put("totalOrders", String.valueOf(row[1]));

                    // Assuming the product state is in the third column of the query result
                    String productState = (String) row[2];
                    String totalOrdersForState = String.valueOf(row[3]);

                    // Put the product state in the map
                    productStatistics.put("totalOrdersForState", totalOrdersForState);

                    return productStatistics;
                })
                .collect(Collectors.toList());
    }

}
