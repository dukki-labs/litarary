package com.litarary.controller;

import com.litarary.dto.HealthDto;
import com.litarary.entity.Health;
import com.litarary.service.HealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthService healthService;

    @GetMapping("/")
    public String index() {
        return "Let's Start Litarary project~!!";
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
