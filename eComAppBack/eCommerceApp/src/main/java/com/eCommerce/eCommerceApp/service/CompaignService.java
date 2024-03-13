package com.eCommerce.eCommerceApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Client;
import com.eCommerce.eCommerceApp.entity.Compaign;
import com.eCommerce.eCommerceApp.entity.CompaignCost;
import com.eCommerce.eCommerceApp.entity.Cost;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.entity.OrderProduct;
import com.eCommerce.eCommerceApp.entity.Orders;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.entity.ProductCost;
import com.eCommerce.eCommerceApp.repository.CompaignCostRepository;
import com.eCommerce.eCommerceApp.repository.CompaingRepostiroy;
import com.eCommerce.eCommerceApp.repository.ProductCostRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CompaignService {

    @Autowired
    CompaignCostRepository costRep;
    @Autowired
    CompaingRepostiroy compaingRep;
    @Autowired
    ProductRepository prodRep;

    public void editCompaign(Map<String, String> compaignInfo){
        // Long currentCompaignId = Long.parseLong(compaignInfo.get(""))
        // Compaign currentCompaign = 
    }

    public List<Compaign> getAllCompaigns(){
        return compaingRep.findAll() ;
    }

    public void addCompaignToProduct(Map<String, String> compaignInfo) {

        Compaign compaign = new Compaign();
        // ObjectMapper objectMapper = new ObjectMapper();
        // JsonNode compaignInfo = objectMapper.readTree(CompaignInfoString);

        Calendar calendar = Calendar.getInstance();
        compaign.setCreationDate(calendar.getTime());

        compaign.setName(compaignInfo.get("name"));
        compaign.setDescription(compaignInfo.get("description"));

        compaign.setIsActive(true);

        // currentCost and newCost
        Float compaignCostAmount = Float.parseFloat(compaignInfo.get("amount"));
        CompaignCost currentCost =createProductCostWithoutCompaign(compaignCostAmount);
        CompaignCost newCost =createProductCostWithoutCompaign(compaignCostAmount);
        
        compaign.setCurrentCost(currentCost);
        compaign.setNewCost(newCost);
        // // setting the costs list and currentCostId
        // Float compaignCostAmount = Float.parseFloat(compaignInfo.get("amount"));
        // CompaignCost currentCost =
        // createProductCostWithoutCompaign(compaignCostAmount);
        // List<CompaignCost> costs = new ArrayList<>();
        // costs.add(currentCost);
        // compaign.setCosts(costs);

        // Long currentCostId = currentCost.getIdCost();
        // compaign.setCurrentCostId(currentCostId);

        // product
        Long compaignProductId = Long.parseLong(compaignInfo.get("productId"));
        Product compaignProduct = prodRep.findById(compaignProductId).get();
        compaign.setProduct(compaignProduct);

        compaingRep.save(compaign);
        currentCost.setCompaign(compaign);
        costRep.save(currentCost);
        newCost.setCompaign(compaign);
        costRep.save(newCost);
        compaingRep.save(compaign);

    }

    public CompaignCost createProductCostWithoutCompaign(float costAmount) {
        CompaignCost cost = new CompaignCost();
        Calendar calendar = Calendar.getInstance();
        cost.setCreationDate(calendar.getTime());

        cost.setCostAmount(costAmount);

        costRep.save(cost);

        return (cost);
    }
}
