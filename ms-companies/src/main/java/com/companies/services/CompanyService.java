package com.companies.services;

import com.companies.entities.Company;

public interface CompanyService {
    Company createCompany(Company company);
    Company readCompany(String name);
    Company updateCompany(Company company, String name);
    void deleteCompany(String name);
}
