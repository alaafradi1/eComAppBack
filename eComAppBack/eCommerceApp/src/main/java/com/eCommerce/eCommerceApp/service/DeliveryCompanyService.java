package com.eCommerce.eCommerceApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.entity.DeliveryCompany;
import com.eCommerce.eCommerceApp.repository.DeliveryCompanyRepository;

@Service
public class DeliveryCompanyService {

  @Autowired
  DeliveryCompanyRepository dcr;

  public void addDeliveryCompany(DeliveryCompany dc) {
    dcr.save(dc);
  }

  public List<DeliveryCompany> getAllDeliveryCompanies() {
    return dcr.findAll();
  }

  public List<DeliveryCompany> getActiveDeliveryCompanies() {
    return dcr.findByIsActiveTrue();
  }

  public void updateDeliveryCompany(DeliveryCompany dc) {
    dcr.save(dc);
  }

  // public void activateDeliveryCompany(DeliveryCompany dc) {
  //   dcr.save(dc);
  // }

}
