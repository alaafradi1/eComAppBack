package com.eCommerce.eCommerceApp.entity;
//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Caisse {

	//private String productName; // commented alaa 
	//private double revenueTotale;// commented alaa 

	// addition alaa 03
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCaisse;
	private float amount;

	@Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;
	private String type;
	private String involvedParty;	
	private String description;
	private Date operationDate;

	public Caisse() {
        // No need to set the ID here; it will be generated by the database.
		// setting the timeZone to tunisian timeZone
		TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

	 // Getter and setter methods for 'idCaisse'
	 public Long getIdCaisse() {
        return idCaisse;
    }

    public void setIdCaisse(Long idCaisse) {
        this.idCaisse = idCaisse;
    }

	// Getter and setter methods for 'operationDate'
	 public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    // Getter and setter methods for 'amount'
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    // Getter and setter methods for 'creationDate'
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    // Getter and setter methods for 'type'
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and setter methods for 'involvedParty'
    public String getInvolvedParty() {
        return involvedParty;
    }

    public void setInvolvedParty(String involvedParty) {
        this.involvedParty = involvedParty;
    }

    // Getter and setter methods for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
