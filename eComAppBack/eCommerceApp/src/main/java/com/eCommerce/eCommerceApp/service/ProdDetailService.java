package com.eCommerce.eCommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.ProdDetail;
import com.eCommerce.eCommerceApp.repository.ProdDetailRepo;

@Service
public class ProdDetailService {


	@Autowired
	ProdDetailRepo pdr;
	
	public List<ProdDetail>getAllProdDetail(){
		return pdr.findAll();
	}
	
	public void addProdDetail(ProdDetail c) {
		pdr.save(c);
	}
	
	public void editProdDetail(ProdDetail c) {
		pdr.save(c);
	}
	
	public void deleteProdDetail(long id) {
		pdr.deleteById(id);
	}

}
