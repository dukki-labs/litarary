package com.litarary.health.controller;

import com.litarary.health.controller.dto.HealthResponse;
import com.litarary.health.dto.HealthDto;
import com.litarary.health.entity.Health;
import com.litarary.health.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthService healthService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public HealthResponse index() {
        return HealthResponse.builder()
                .projectName("Litarary")
                .statusMessage("Let's start project")
                .build();
    }

    @GetMapping("/health-check")
    public List<Health> getStatus() {
        return healthService.getHealthInfo();
    }

    @PostMapping("/create-health")
    public void insertHealth(@RequestBody HealthDto dto) {
        healthService.insertHealth(dto);
    }
}
