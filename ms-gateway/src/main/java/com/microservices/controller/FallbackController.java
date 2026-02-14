package com.microservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/companies")
    public ResponseEntity<String> companiesFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de compañías no está disponible temporalmente. Por favor, intente más tarde.");
    }

    @GetMapping("/report")
    public ResponseEntity<String> reportFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("El servicio de reportes no está disponible temporalmente. Por favor, intente más tarde.");
    }
}
