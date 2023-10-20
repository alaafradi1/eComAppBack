package com.eCommerce.eCommerceApp.entity;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Client {
     @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCaisse;

    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;
    private String name;
	private String address;	
	private String phoneNumber;
    private String governorate;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Orders order;

    public Client() {
        // setting the timeZone to tunisian timeZone
        TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }






}
