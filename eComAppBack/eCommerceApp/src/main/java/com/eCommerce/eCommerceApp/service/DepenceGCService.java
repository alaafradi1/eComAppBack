package com.eCommerce.eCommerceApp.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import com.eCommerce.eCommerceApp.entity.DepenceGC;
import com.eCommerce.eCommerceApp.repository.DepenceGCRepo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepenceGCService {

	@Autowired
	DepenceGCRepo depGCRep;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Transactional
	public void updRevenueCaisse(String nomProduit, double depence) {
	    System.out.println("le nomProduit est : " + nomProduit);
	    System.out.println("le depence : " + depence);
	    String jpql = "UPDATE Caisse c SET c.revenueTotale = c.revenueTotale - :depence WHERE c.productName = :nomArt";
	    Query query = entityManager.createQuery(jpql);
	    query.setParameter("depence", depence);
	    query.setParameter("nomArt", nomProduit);
	    int rowsUpdated = query.executeUpdate();
	    System.out.println("Number of rows updated: " + rowsUpdated);
	}

	
	public List<DepenceGC> getAllDepencesGC(){
		return depGCRep.findAll();
	}
	
	public void addDepencesGC(DepenceGC c) {
		depGCRep.save(c);
	}
	
	public void updateDepencesGC(DepenceGC c) {
		depGCRep.save(c);
	}
	
	public void deleteDepencesGC(long id) {
		depGCRep.deleteById(id);
	}
}
