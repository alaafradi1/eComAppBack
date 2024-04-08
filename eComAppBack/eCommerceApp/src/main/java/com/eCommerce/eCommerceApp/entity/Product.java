package com.eCommerce.eCommerceApp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Id;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private float productPrice;
    
    
    private String rowColor;
    private Boolean isActive;
    
   

    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Orders> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderProduct> orderProducts = new ArrayList<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Campaign> campaigns = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnore
    @OrderBy("creationDate DESC")
    private List<ProductCost> costs;

    // @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    // @JoinColumn(name = "current_cost_id")
    private Long currentCostId;


      public List<Campaign> getcampaigns() {
        return campaigns;
    }

    public void setcampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }
    
    public Long getCurrentCostId() {
        return currentCostId;
    }

    public void setCurrentCostId(Long currentCostId) {
        this.currentCostId = currentCostId;
    }

    public List<ProductCost> getCosts() {
        return costs;
    }

    public void setCosts(List<ProductCost> costs) {
        this.costs = costs;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
    public Long getProductId() {
        return productId;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

     public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
   

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    // public float getProductCost() {
    //     return productCost;
    // }

    // public void setProductCost(float productCost) {
    //     this.productCost = productCost;
    // }

    // public float getProductAddCost() {
    //     return productAddCost;
    // }

    // public void setProductAddCost(float productAddCost) {
    //     this.productAddCost = productAddCost;
    // }

    // Getter and setter methods for 'companyName'
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
