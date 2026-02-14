package com.reports.repositories;

import com.reports.config.LoadBalancerConfiguration;
import com.reports.dto.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "ms-companies")
@LoadBalancerClient(name = "ms-companies", configuration = LoadBalancerConfiguration.class)
public interface CompaniesRepository {
    @GetMapping(path = "/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping(path = "/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping(path = "/company/{name}")
    void deleteByName(@PathVariable String name);
}
