package com.eCommerce.eCommerceApp.entity;



import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Produit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduit;
	

	private String societeLivraison;
	private Date mois;
	private Long colisImprimer;
	public Long colisPayer;
	private Long retour;
	private double comTnd;
	private double coutAchat;
	private double pourcentageRetour; // calculable cbon
	private double revenue; // calculable cbon
	
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name="fl_prod_detail")
  //  private ProdDetail produitDetail;
    
    
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "caisse_id")
   // private Caisse caisse;
    
    

    
	// public Caisse getCaisse() {
	// 	return caisse;
	// }

	// public void setCaisse(Caisse caisse) {
	// 	this.caisse = caisse;
	// }

	// public ProdDetail getProduitDetail() {
	// 	return produitDetail;
	// }

	// public void setProduitDetail(ProdDetail produitDetail) {
	// 	this.produitDetail = produitDetail;
	// }

	public long getIdProduit() {
		return idProduit;
	}

	public String getSocieteLivraison() {
		return societeLivraison;
	}

	public void setSocieteLivraison(String societeLivraison) {
		this.societeLivraison = societeLivraison;
	}

	public Date getMois() {
		return mois;
	}

	public void setMois(Date mois) {
		this.mois = mois;
	}

	public Long getColisImprimer() {
		return colisImprimer;
	}

	public void setColisImprimer(Long colisImprimer) {
		this.colisImprimer = colisImprimer;
	}

	public Long getColisPayer() {
		return colisPayer;
	}

	public void setColisPayer(Long colisPayer) {
		this.colisPayer = colisPayer;
	}

	public Long getRetour() {
		return retour;
	}

	public void setRetour(Long retour) {
		this.retour = retour;
	}

	public double getCom() {
		return comTnd;
	}

	public void setCom(double comTnd) {
		this.comTnd = comTnd;
	}

	public double getCoutAchat() {
		return coutAchat;
	}

	public void setCoutAchat(double coutAchat) {
		this.coutAchat = coutAchat;
	}

	public double getPourcentageRetour() {
		return pourcentageRetour;
	}

	public void setPourcentageRetour() {
	    if (colisImprimer != 0) {
	        this.pourcentageRetour = Math.round(((double) retour / colisImprimer) * 100 * 100.0) / 100.0;
	    } else {
	        this.pourcentageRetour = 0.0; // Avoid division by zero
	    }
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue() {
	    // if (produitDetail != null) {
	    //     double prixSansLivraison = produitDetail.getPrixSansLivraison();
	    //     double coutAchatUnitaire = produitDetail.getCoutAchatUnitaire();
	    //     double calculatedRevenue = (colisPayer * prixSansLivraison) - (colisPayer * coutAchatUnitaire) - comTnd;
	    //     System.out.println("prixSansLivraison : " + prixSansLivraison);
	    //     System.out.println("coutAchatUnitaire : " + coutAchatUnitaire);
	    //     System.out.println("comTnd : " + comTnd);
	    //     System.out.println("calculated revenue : " + calculatedRevenue);
	    //     // Set the revenue attribute with two decimal places
	    //     this.revenue = Math.round(calculatedRevenue * 100.0) / 100.0;
	    // } else {
	    //     this.revenue = 0.0; // Handle the case where no associated ProdDetail is set
	    // }
	}


	
}
