package com.litarary.health.repository;

import com.litarary.health.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckRepository extends JpaRepository<Health, Long> {
}
