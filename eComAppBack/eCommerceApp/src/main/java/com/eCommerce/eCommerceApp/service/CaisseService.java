package com.eCommerce.eCommerceApp.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.repository.CaisseRepository;

@Service
public class CaisseService {

	@Autowired
	CaisseRepository cr;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Double getTotalRevenue(String nomProduit) {
		System.out.println("le nomProduit est : "+nomProduit);
	    //String jpql = "SELECT SUM(p.revenue) FROM Produit p WHERE p.produitDetail.nomArticle = :nomArt";
	    String jpql = "SELECT SUM(c.prix) FROM CreerColis c WHERE c.nomProduit = :nomArt";
	    TypedQuery<Double> query = entityManager.createQuery(jpql, Double.class);
	    query.setParameter("nomArt", nomProduit);
	    Double totalRevenue = query.getSingleResult();
	    System.out.println("le revenue tot est : "+totalRevenue);
	    return (totalRevenue != null) ? totalRevenue : 0.0;
	}
	//String jpql = "SELECT SUM(p.revenue) FROM Produit p WHERE p.produitDetail.nomArticle = 'germancare' ";

	public Double getMontant(String nomArticle) {
	    String jpql = "SELECT p.montant FROM DepenceGC p WHERE p.nomArticle = :nomArtc";
	    TypedQuery<Double> query = entityManager.createQuery(jpql, Double.class);
	    query.setParameter("nomArtc", nomArticle);
	    Double totalRevenue = query.getSingleResult();
	    System.out.println("totalRevenue : " + totalRevenue);
	    return (totalRevenue != null) ? totalRevenue : 0.0;
	}

	public List<Caisse>getAllInfos(){
		return cr.findAll();
	}
	
	public void addInfo(Caisse c) {
		cr.save(c);
	}
	
	public void editInfo(Caisse c) {
		cr.save(c);
	}
	
	public void deleteInfo(int id) {
		cr.deleteById(id);
	}
}
