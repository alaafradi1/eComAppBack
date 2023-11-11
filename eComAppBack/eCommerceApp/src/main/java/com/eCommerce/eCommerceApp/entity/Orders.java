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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eCommerce.eCommerceApp.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Orders {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrder;
    
    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;

    private String state;
	private String note;	
	private String printState;
    private Float orderPrice;
 
    // private String deliveryCompany;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
   
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @JsonManagedReference
    private Client client;

     // @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "idDC") 
    private DeliveryCompany deliveryCompany;

    public Orders() {
        // setting the timeZone to tunisian timeZone
        TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

      public Number getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public DeliveryCompany getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(DeliveryCompany deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }


     public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Product getProduct() {
        return product;
    }


    public Long getIdOrder() {
        return idOrder;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // public String getDeliveryCompany() {
    //     return deliveryCompany;
    // }

    // public void setDeliveryCompany(String deliveryCompany) {
    //     this.deliveryCompany = deliveryCompany;
    // }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrintState() {
        return printState;
    }

    public void setPrintState(String printState) {
        this.printState = printState;
    }

    
     




}
