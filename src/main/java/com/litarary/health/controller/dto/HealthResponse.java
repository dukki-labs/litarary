package com.litarary.health.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HealthResponse {
    private String projectName;
    private String statusMessage;
}
