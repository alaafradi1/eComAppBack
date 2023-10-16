package com.eCommerce.eCommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.CreerColis;
import com.eCommerce.eCommerceApp.repository.CreerColisRepo;

@Service
public class CreerColisService {


	@Autowired
	CreerColisRepo ccrp;
	
	public List<CreerColis>getAllCreerColis(){
		return ccrp.findAll();
	}
	
	public void addCreerColis(CreerColis c) {
		ccrp.save(c);
	}
	
	public void editCreerColis(CreerColis c) {
		ccrp.save(c);
	}
	
	public void deleteCreerColis(long id) {
		ccrp.deleteById(id);
	}
}
