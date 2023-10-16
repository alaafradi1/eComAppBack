package com.eCommerce.eCommerceApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProdDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProdDetail;
	
	private String nomArticle;
	private double revenueUnitaire; // calculable cbon 
	private double coutAchatUnitaire;
	private double prixVente;
	private double coutLivraison;
	private double coutUnitaireCom;
	private double prixSansLivraison; // calculable cbon 



	
	public Long getIdProdDetail() {
		return idProdDetail;
	}

	public void setIdProdDetail(Long idProdDetail) {
		this.idProdDetail = idProdDetail;
	}


	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public double getCoutAchatUnitaire() {
		return coutAchatUnitaire;
	}

	public void setCoutAchatUnitaire(double coutAchatUnitaire) {
		this.coutAchatUnitaire = coutAchatUnitaire;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}
	// ----------------------
	public double getCoutLivraison() {
		return coutLivraison;
	}

	public void setCoutLivraison(double coutLivraison) {
		this.coutLivraison = coutLivraison;
	}

	public double getCoutUnitaireCom() {
		return coutUnitaireCom;
	}

	public void setCoutUnitaireCom(double coutUnitaireCom) {
		this.coutUnitaireCom = coutUnitaireCom;
	}

	public double getPrixSansLivraison() {
		return prixSansLivraison;
	}

	public void setPrixSansLivraison(double prixSansLivraison) {
		this.prixSansLivraison = this.prixVente - this.coutLivraison;
//		System.out.println("prixSansLivraison" + prixSansLivraison);
//		System.out.println("prix vente" + prixVente);
//		System.out.println("coutLivraison" + coutLivraison);
	}
	
	public double getRevenueUnitaire() {
	    return revenueUnitaire;
	}

	public void setRevenueUnitaire(double revenueUnitaire) {
	    this.revenueUnitaire = this.prixVente - (this.coutLivraison + this.coutUnitaireCom + this.coutAchatUnitaire);
		System.out.println("revenueUnitaire" + this.revenueUnitaire);
		System.out.println("prixVente" + this.prixVente);
		System.out.println("coutLivraison" + this.coutLivraison);
		System.out.println("coutUnitaireCom" + this.coutUnitaireCom);
		System.out.println("coutAchatUnitaire" + this.coutAchatUnitaire);
	}
}
