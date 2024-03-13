package com.eCommerce.eCommerceApp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CompaignCost extends Cost {
    
    @ManyToOne
    @JoinColumn(name = "compaign_id")
    @JsonIgnore
    private Compaign compaign;

    public Compaign getCompaign() {
        return compaign;
    }

    public void setCompaign(Compaign compaign) {
        this.compaign = compaign;
    }
}
