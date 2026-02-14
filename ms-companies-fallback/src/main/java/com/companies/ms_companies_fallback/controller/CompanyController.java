package com.companies.ms_companies_fallback.controller;

import com.companies.ms_companies_fallback.dto.Company;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping(path = "company")
@Slf4j
//@Tag(name = "Companies Resources")
public class CompanyController {

    private static final Company DEFAULT_COMPANY = Company
            .builder()
            .id(0L)
            .founder("Fallback")
            .name("Fallback company")
            .logo("http://default-logo.com")
            .foundation_date(LocalDate.now())
            .webSite(Collections.emptyList())
            .build();

    @GetMapping(path = "{name}")
    public ResponseEntity<Company> getCompany(@PathVariable String name){
        log.info("Get: company in fallback {}", name);
        return ResponseEntity.ok(DEFAULT_COMPANY);
    }

}
