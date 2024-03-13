package com.eCommerce.eCommerceApp.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.Cost;
import com.eCommerce.eCommerceApp.entity.Product;
import com.eCommerce.eCommerceApp.entity.ProductCost;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;
import com.eCommerce.eCommerceApp.repository.CompanyRepository;
import com.eCommerce.eCommerceApp.repository.ProductCostRepository;
import com.eCommerce.eCommerceApp.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository pr;
    @Autowired
    CompanyRepository cr;
    @Autowired
    ProductCostRepository costRep;

    public void addProduct(Map<String, String> productWithCompany) {
        Product product = new Product();
        Company company = cr.findById(Long.parseLong(productWithCompany.get("idCompany"))).get();
        product.setCompany(company);
        // product.setProductCost(Float.parseFloat(productWithCompany.get("productCost")));

        product.setProductName(productWithCompany.get("productName"));
        product.setProductPrice(Float.parseFloat(productWithCompany.get("productPrice")));

        product.setRowColor(productWithCompany.get("rowColor"));
        product.setIsActive(true);

        Calendar calendar = Calendar.getInstance();
        product.setCreationDate(calendar.getTime());

        // Cost currentCost = createCostWithoutProduct(Float.parseFloat(productWithCompany.get("productCost")));
        // product.setCurrentCost(currentCost);
        // setting the list of costs
        Float productCostAmount = Float.parseFloat(productWithCompany.get("productCost"));
        ProductCost currentCost = createProductCostWithoutProduct(productCostAmount);
        List<ProductCost> costs = new ArrayList<>();
        costs.add(currentCost);
        product.setCosts(costs);

        // setting the currentCostId
        Long currentCostId = currentCost.getIdCost();
        product.setCurrentCostId(currentCostId);

        pr.save(product);
        currentCost.setProduct(product);
        costRep.save(currentCost);
        pr.save(product);
    }

     public ProductCost createProductCostWithoutProduct(float costAmount){
        ProductCost cost = new ProductCost();
        Calendar calendar = Calendar.getInstance();
        cost.setCreationDate(calendar.getTime());

        cost.setCostAmount(costAmount);

        costRep.save(cost);
        
        return(cost);
    }

    public ProductCost createCostWithoutProduct(float costAmount){
        ProductCost cost = new ProductCost();
        Calendar calendar = Calendar.getInstance();
        cost.setCreationDate(calendar.getTime());

        cost.setCostAmount(costAmount);

        costRep.save(cost);
        
        return(cost);
    }

    public ProductCost createCostWithProduct(float costAmount, Product existingProduct){
        ProductCost cost = new ProductCost();
        Calendar calendar = Calendar.getInstance();
        cost.setCreationDate(calendar.getTime());

        cost.setCostAmount(costAmount);

        cost.setProduct(existingProduct);

        costRep.save(cost);
        
        return(cost);
    }

    public List<Product> getAllProducts() {
        return pr.findAll();
    }

    public List<Product> getActiveProducts() {
        return pr.findByIsActiveTrue();
    }

    public ResponseEntity<String> editProduct(Long productId, Map<String, String> productWithCompanyId) {
       try {

        Product existingProduct = pr.findById(productId).get();
			if (existingProduct == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found using productId");
			}

			existingProduct.setProductName(productWithCompanyId.get("productName"));
			existingProduct.setRowColor(productWithCompanyId.get("rowColor"));
			existingProduct.setProductPrice(Float.parseFloat(productWithCompanyId.get("productPrice")));
			// existingProduct.setProductCost(Float.parseFloat(productWithCompanyId.get("productCost")));
			Company newCompany = cr.findById(Long.parseLong(productWithCompanyId.get("idCompany"))).get();
			existingProduct.setCompany(newCompany);

            // updating the oldCosts and currentCost
            // Cost oldCurrentcost = existingProduct.getCurrentCost();
            Long oldCurrentcostId = existingProduct.getCurrentCostId();
            ProductCost oldCurrentcost = costRep.findById(oldCurrentcostId).get();
            Float oldCurrentCostAmount = oldCurrentcost.getCostAmount();

            Float newCurrentCostAmount = Float.parseFloat(productWithCompanyId.get("currentCost"));
           
            if(!oldCurrentCostAmount.equals(newCurrentCostAmount)){
                ProductCost newCurrentCost = createCostWithProduct(Float.parseFloat(productWithCompanyId.get("currentCost")),existingProduct);
                
                // oldCosts gets updated automatically
                // List<Cost> newCosts = existingProduct.getCosts();
                // newCosts.add(newCurrentCost);
                // existingProduct.setCosts(newCosts);

                existingProduct.setCurrentCostId(newCurrentCost.getIdCost());

                // existingProduct.setCurrentCost(newCurrentCost);
            } 
            // else{
            //     existingProduct.setCurrentCost(oldCurrentcost);
            //     // no need to update the oldCosts in this case
            // }

            pr.save(existingProduct);      

			return ResponseEntity.ok("Product updated successfully");
		} catch (Exception e) {
			String errorMessage = "Error updating product: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		}

    }
    // public void editProduct(Product product) {
    //     Product oldProduct = pr.findById(product.getProductId()).get();
    //     // saving old cost in the oldCosts if cost is changed 
        
    //     Cost oldCurrentCost = oldProduct.getCurrentCost();
    //     Cost newCurrentCost = product.getCurrentCost();
    //     if(!newCurrentCost.equals(oldCurrentCost))
    //     {
    //         List<Cost> oldCosts = oldProduct.getOldCosts();
    //         oldCosts.add(oldCurrentCost);
                        
    //         product.setOldCosts(oldCosts);
    //     }
    //     pr.save(product);
    // }

    public void activateProduct(Product product) {
       pr.save(product);
    }
    

}
