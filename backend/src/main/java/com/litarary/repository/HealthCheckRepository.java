package com.litarary.repository;

import com.litarary.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckRepository extends JpaRepository<Health, Long> {
}
