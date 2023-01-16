package com.litarary.service;

import com.litarary.dto.HealthDto;
import com.litarary.entity.Health;
import com.litarary.repository.HealthCheckRepository;
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
