package com.litarary.book.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDetail {

    private boolean recommendUseYn;
    private BookContent bookContent;
}
