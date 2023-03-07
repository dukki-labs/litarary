package com.litarary.book.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcernBookDto {

    @Getter
    @Builder
    public static class Request {
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate inquiryDate;
    }

    @Getter
    @Builder
    public static class Response {
        private List<ConcernBookTypeDto> concernBookTypeDto;
    }
}
