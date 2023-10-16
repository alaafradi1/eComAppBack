package com.eCommerce.eCommerceApp.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CreerColis {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCreerColis;
	
	private String etat;
	private Date dateCreation;
	private double prix;
	private Long numeroPortable;
	private String nomComplet;
	private String adresse;
	private String gouvernorat;
	private String nomProduit;
	private String notes;
	private String livreur;
	private boolean imprimer;
	
	
	
	public boolean isImprimer() {
		return imprimer;
	}
	public void setImprimer(boolean imprimer) {
		this.imprimer = imprimer;
	}
	public String getLivreur() {
		return livreur;
	}
	public void setLivreur(String livreur) {
		this.livreur = livreur;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Long getIdCreerColis() {
		return idCreerColis;
	}
	public void setIdCreerColis(Long idCreerColis) {
		this.idCreerColis = idCreerColis;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Long getNumeroPortable() {
		return numeroPortable;
	}
	public void setNumeroPortable(Long numeroPortable) {
		this.numeroPortable = numeroPortable;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getGouvernorat() {
		return gouvernorat;
	}
	public void setGouvernorat(String gouvernorat) {
		this.gouvernorat = gouvernorat;
	}
	public String getNomProduit() {
		return nomProduit;
	}
	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
}
