package com.eCommerce.eCommerceApp.entity;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class DeliveryCompany {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDC;
    private String name;
	private float deliveryPrice;
    private String rowColor;
    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;
    private Boolean isActive;
    
     //   @JsonBackReference

    
    @OneToMany(mappedBy = "deliveryCompany", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Orders> orders;

    public DeliveryCompany() {
        
        // setting the timeZone to tunisian timeZone
        TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }


      public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    // public Orders getOrder() {
    //     return order;
    // }
    // public void setOrder(Orders order) {
    //     this.order = order;
    // }
    public Long getIdDC() {
        return idDC;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Number getDeliveryPrice() {
        return deliveryPrice;
    }
    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }	

    
}
