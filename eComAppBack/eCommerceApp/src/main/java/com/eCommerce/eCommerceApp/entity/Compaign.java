package com.eCommerce.eCommerceApp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Compaign {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCompaign;

    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;// the date when the caisse is added
   
    private String name;// a Revenu(+) or a Depense(-)
	private String description;	

    @ManyToOne
    // @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    // @OneToMany(mappedBy = "compaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // // @JsonIgnore
    // @OrderBy("creationDate DESC")
    // private List<CompaignCost> costs;

    // private Long currentCostId;

    @OneToOne
    private CompaignCost currentCost;

    @OneToOne
    private CompaignCost newCost;


    public CompaignCost getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(CompaignCost currentCost) {
        this.currentCost = currentCost;
    }

    public CompaignCost getNewCost() {
        return newCost;
    }

    public void setNewCost(CompaignCost newCost) {
        this.newCost = newCost;
    }

    private Boolean isActive;

    
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // public Long getCurrentCostId() {
    //     return currentCostId;
    // }

    // public void setCurrentCostId(Long currentCostId) {
    //     this.currentCostId = currentCostId;
    // }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    // public List<CompaignCost> getCosts() {
    //     return costs;
    // }

    // public void setCosts(List<CompaignCost> costs) {
    //     this.costs = costs;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    
    
}
