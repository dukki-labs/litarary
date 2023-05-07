package com.litarary.profile.controller.dto;

import com.litarary.profile.domain.RegisterDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RegisterBookDto {
    @Getter
    @AllArgsConstructor
    public static class Request {
        @Min(1)
        private int page;
        @Min(10)
        @Max(50)
        private int size;
        @NotNull
        private RegisterDate registerDate;
    }
}
