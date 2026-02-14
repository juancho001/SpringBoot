package com.reports.services.Impl;

import com.reports.dto.Company;
import com.reports.dto.WebSite;
import com.reports.helpers.ReportHelpers;
import com.reports.repositories.CompaniesFallbackRepository;
import com.reports.repositories.CompaniesRepository;
import com.reports.services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final ReportHelpers reportHelpers;
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;

//    private final EurekaClient eurekaClient;

    @Override
    public String makeReport(String name) {
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(() -> this.makeReportMain(name), throwable -> this.makeReportFallback(name, throwable));
    }

    @Override
    public String saveReport(String name) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelpers.getPlaceholdersFromTemplate(name);
        var webSites = Stream.of(placeholders.get(3)).map(website-> WebSite.builder().name(website).build()).toList();
        var company = Company.builder()
                .name(placeholders.get(0))
                .foundation_date(LocalDate.parse(placeholders.get(1),format))
                .founder(placeholders.get(2))
                .webSite(webSites)
                .build();
        this.companiesRepository.postByName(company);
        return "Saved...";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deleteByName(name);
    }


    private String makeReportMain(String name) {
        return reportHelpers.readTemplate(this.companiesRepository.getByName(name).orElseThrow(() -> new RuntimeException("Company not found")));
    }

    private String makeReportFallback(String name, Throwable throwable) {
        log.warn(throwable.getMessage());
        return reportHelpers.readTemplate(this.companiesFallbackRepository.getByName(name));
    }
}
