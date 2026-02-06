package com.companies.services.impl;

import com.companies.entities.Category;
import com.companies.entities.Company;
import com.companies.repositories.CompanyRepository;
import com.companies.services.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        company.getWebSite().forEach(webSite -> {
            if(Objects.isNull(webSite.getCategory())){
                webSite.setCategory(Category.NONE);
            }
        });
        return this.companyRepository.save(company);
    }

    @Override
    public Company readCompany(String name) {
        return this.companyRepository.findByName(name).orElseThrow(()-> new RuntimeException("Company not found..."));
    }

    @Override
    public Company updateCompany(Company company, String name) {
        var companyToUpdate = this.companyRepository.findByName(name).orElseThrow(()-> new RuntimeException("Company not found"));
        companyToUpdate.setLogo(company.getLogo());
        companyToUpdate.setFoundation_date(company.getFoundation_date());
        companyToUpdate.setFounder(company.getFounder());
        return this.companyRepository.save(companyToUpdate);
    }

    @Override
    public void deleteCompany(String name) {
        var companyToDelete = this.companyRepository.findByName(name).orElseThrow(()-> new RuntimeException("Company not found"));
        this.companyRepository.delete(companyToDelete);
    }
}
