package com.eCommerce.eCommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Produit;
import com.eCommerce.eCommerceApp.repository.ProduitRepository;

@Service
public class ProduitService {

	@Autowired
	ProduitRepository pr;
	
	public List<Produit>getAllProduits(){
		return pr.findAll();
	}
	
	public void addProduit(Produit c) {
		pr.save(c);
	}
	
	public void editProduit(Produit c) {
		pr.save(c);
	}
	
	public void deleteProduit(long id) {
		pr.deleteById(id);
	}
	
	
	public Produit getProduit(long id) {
		System.out.println(pr.getOne(id));
		return pr.getOne(id);
	}	
}
