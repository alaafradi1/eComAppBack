package com.eCommerce.eCommerceApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerceApp.entity.Caisse;
import com.eCommerce.eCommerceApp.entity.Company;
import com.eCommerce.eCommerceApp.repository.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository cr;

    public void addCompany(Company c) {
        cr.save(c);
        // hs.addCaisseHistory(c);
    }

    public List<Company> getAllCompanies() {
        return cr.findAll();
    }

    public void deleteCompany(Long idCompany) {
        cr.deleteById(idCompany);
    }

}
