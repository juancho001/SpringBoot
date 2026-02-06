package com.companies.controller;

import com.companies.dto.CompanyDTO;
import com.companies.entities.Company;
import com.companies.services.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(path = "company")
@Slf4j
@Tag(name = "Companies Resources")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Get company by name")
    @GetMapping(path = "{name}")
    public ResponseEntity<Company> getCompany(@PathVariable String name){
        log.info("Get: company {}", name);
        return ResponseEntity.ok(this.companyService.readCompany(name));
    }

    @Operation(summary = "Create company")
    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody Company company){
            log.info("Create: company {}", company);
        return ResponseEntity.created(URI.create(this.companyService.createCompany(company).getName())).build();
    }

    @Operation(summary = "Update company")
    @PutMapping(path = "name")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable String name){
        log.info("Upate: company {}", name);
        return ResponseEntity.ok(this.companyService.updateCompany(company, name));
    }

    @Operation(summary = "Delete company")
    @DeleteMapping(path = "name")
    public ResponseEntity<?> deleteCompany(@PathVariable String name){
        log.info("Delete: company {}", name);
        this.companyService.deleteCompany(name);
        return ResponseEntity.noContent().build();
    }

}
