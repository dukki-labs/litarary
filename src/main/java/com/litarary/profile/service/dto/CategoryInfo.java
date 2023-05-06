package com.litarary.profile.service.dto;

import com.litarary.account.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryInfo {
    private long categoryId;
    private BookCategory bookCategory;
}
