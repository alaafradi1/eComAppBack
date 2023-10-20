	package com.eCommerce.eCommerceApp.entity;



import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DepenceGC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDepence;
	
	private Date dateDepence;
	private String nomArticle;
	private String nomProduitAcheter;
	private double montant;
	private String nomMembre;
	
	//@ManyToOne
  //  @JoinColumn(name = "caisse_id")
   // private Caisse caisse;
	

	// public void setCaisse(Caisse caisse) {
	// 	this.caisse = caisse;
	// }
	
	// public Caisse getCaisse() {
	// 	return caisse;
	// }

	public Long getIdDepence() {
		return idDepence;
	}
	public void setIdDepence(Long idDepence) {
		this.idDepence = idDepence;
	}
	public Date getDateDepence() {
		return dateDepence;
	}
	public void setDateDepence(Date dateDepence) {
		this.dateDepence = dateDepence;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getNomProduitAcheter() {
		return nomProduitAcheter;
	}
	public void setNomProduitAcheter(String nomProduitAcheter) {
		this.nomProduitAcheter = nomProduitAcheter;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public String getNomMembre() {
		return nomMembre;
	}
	public void setNomMembre(String nomMembre) {
		this.nomMembre = nomMembre;
	}
	
	
	
}
