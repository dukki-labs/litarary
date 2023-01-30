package com.litarary.health.service;

import com.litarary.health.dto.HealthDto;
import com.litarary.health.entity.Health;
import com.litarary.health.repository.HealthCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HealthService {

    private final HealthCheckRepository healthCheckRepository;

    public List<Health> getHealthInfo() {
        return healthCheckRepository.findAll();
    }

    @Transactional
    public void insertHealth(HealthDto dto) {
        Health health = new Health();
        health.saveHealth(dto.getStatus(), dto.getCode());
        healthCheckRepository.save(health);
    }
}
