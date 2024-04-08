package com.eCommerce.eCommerceApp.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CampaignCost extends Cost {
    
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    @JsonIgnore
    private Campaign campaign;

    public Campaign getcampaign() {
        return campaign;
    }

    public void setcampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
