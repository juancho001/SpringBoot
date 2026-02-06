package com.companies.services.impl;

import com.companies.entities.Category;
import com.companies.entities.Company;
import com.companies.entities.WebSite;
import com.companies.repositories.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void createCompany() {
        // Given
        Company company = new Company();
        company.setName("Test Company");
        
        WebSite webSite = new WebSite();
        webSite.setName("Test Site");
        // Category is null initially to test the logic that sets it to NONE
        
        company.setWebSite(new HashSet<>(Collections.singletonList(webSite)));

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        // When
        Company result = companyService.createCompany(company);

        // Then
        assertNotNull(result);
        assertEquals(Category.NONE, webSite.getCategory());
        verify(companyRepository).save(company);
    }

    @Test
    void readCompany() {
        // Given
        String companyName = "Test Company";
        Company company = new Company();
        company.setName(companyName);

        when(companyRepository.findByName(companyName)).thenReturn(Optional.of(company));

        // When
        Company result = companyService.readCompany(companyName);

        // Then
        assertNotNull(result);
        assertEquals(companyName, result.getName());
        verify(companyRepository).findByName(companyName);
    }

    @Test
    void readCompany_NotFound() {
        // Given
        String companyName = "NonExistent";
        when(companyRepository.findByName(companyName)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> companyService.readCompany(companyName));
        verify(companyRepository).findByName(companyName);
    }

    @Test
    void updateCompany() {
        // Given
        String companyName = "Test Company";
        Company existingCompany = new Company();
        existingCompany.setName(companyName);
        existingCompany.setFounder("Old Founder");

        Company updateInfo = new Company();
        updateInfo.setFounder("New Founder");
        updateInfo.setLogo("New Logo");
        updateInfo.setFoundation_date(LocalDate.now());

        when(companyRepository.findByName(companyName)).thenReturn(Optional.of(existingCompany));
        when(companyRepository.save(any(Company.class))).thenReturn(existingCompany);

        // When
        Company result = companyService.updateCompany(updateInfo, companyName);

        // Then
        assertNotNull(result);
        assertEquals("New Founder", result.getFounder());
        assertEquals("New Logo", result.getLogo());
        assertEquals(updateInfo.getFoundation_date(), result.getFoundation_date());
        verify(companyRepository).findByName(companyName);
        verify(companyRepository).save(existingCompany);
    }

    @Test
    void deleteCompany() {
        // Given
        String companyName = "Test Company";
        Company company = new Company();
        company.setName(companyName);

        when(companyRepository.findByName(companyName)).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);

        // When
        companyService.deleteCompany(companyName);

        // Then
        verify(companyRepository).findByName(companyName);
        verify(companyRepository).delete(company);
    }
}
