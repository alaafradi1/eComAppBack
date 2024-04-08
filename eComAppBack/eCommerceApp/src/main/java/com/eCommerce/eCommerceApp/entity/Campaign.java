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
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Campaign {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idcampaign;

    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;// the date when the caisse is added
   
    private String name;// a Revenu(+) or a Depense(-)
	private String description;

    @ManyToOne
    // @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JsonIgnore
    @OrderBy("creationDate DESC")
    private List<CampaignCost> costs;

    private Long currentCostId ;

    /** newCostId : when updating the campaign's cost, the cost should stay the same until the end of the day '00:00',
     *  and when the day ends, it should be updated, that y the new cost value will be created and inserted in the 'newCostId' until the day ends.**/
    private Long newCostId ;

    private Boolean isActive; // when changed, it needs to be changed at the end of the day.
    private Boolean newIsActive;

    public List<CampaignCost> getCosts() {
        return costs;
    }

    public void setCosts(List<CampaignCost> costs) {
        this.costs = costs;
    }

    public Long getIdcampaign() {
        return idcampaign;
    }

    public Long getNewCostId() {
        return newCostId;
    }

    public void setNewCostId(Long newCostId) {
        this.newCostId = newCostId;
    }
    public Long getCurrentCostId() {
        return currentCostId;
    }

    public void setCurrentCostId(Long currentCostId) {
        this.currentCostId = currentCostId;
    }


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

    // public List<CampaignCost> getCosts() {
    //     return costs;
    // }

    // public void setCosts(List<CampaignCost> costs) {
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


    public Boolean getNewIsActive() {
        return newIsActive;
    }

    public void setNewIsActive(Boolean newIsActive) {
        this.newIsActive = newIsActive;
    }
}
