package com.eCommerce.eCommerceApp.entity;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class History {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idHistory;
	private String type;
    @Temporal(TemporalType.TIMESTAMP) 
	private Date creationDate;
    private String description;
    private String reason;

    public History(){
        // setting the timeZone to tunisian timeZone
		TimeZone tunisianTimeZone = TimeZone.getTimeZone("Africa/Tunis");
        TimeZone.setDefault(tunisianTimeZone);
    }

    // Getter and setter methods for 'type'
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

     // Getter and setter methods for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     // Getter and setter methods for 'reason'
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // Getter and setter methods for 'creationDate'
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


}
