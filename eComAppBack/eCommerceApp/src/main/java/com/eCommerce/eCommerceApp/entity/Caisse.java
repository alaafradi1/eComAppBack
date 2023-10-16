package com.eCommerce.eCommerceApp.entity;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Caisse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCaisse;
	
	private String productName;
	private double revenueTotale;
	
	@OneToMany(mappedBy = "caisse", cascade = CascadeType.ALL)
    private List<DepenceGC> depences;


	public void setDepences(List<DepenceGC> depences) {
		this.depences = depences;
	}


	public int getIdCaisse() {
		return idCaisse;
	}


	public void setIdCaisse(int idCaisse) {
		this.idCaisse = idCaisse;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public double getRevenueTotale() {
		return revenueTotale;
	}


	public void setRevenueTotale(double revenueTotale) {
		this.revenueTotale = Math.round(revenueTotale * 100.0) / 100.0;
		System.out.println("revenue TTotale : "+this.revenueTotale);
	}

}
