package com.eCommerce.eCommerceApp.entity;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cost {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCost;

    private Float costAmount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    // @ManyToOne
    // @JoinColumn(name = "product_id")
    // @JsonIgnore
    // private Product product;

    // @ManyToOne
    // @JoinColumn(name = "compaign_id")
    // @JsonIgnore
    // private Compaign compaign;

    // public Compaign getCompaign() {
    //     return compaign;
    // }

    // public void setCompaign(Compaign compaign) {
    //     this.compaign = compaign;
    // }

    public Cost() {
        TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

    // public Product getProduct() {
    //     return product;
    // }

    // public void setProduct(Product product) {
    //     this.product = product;
    // }

    public Float getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Float costAmount) {
        this.costAmount = costAmount;
    }

    public Long getIdCost() {
        return idCost;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    


    

}
