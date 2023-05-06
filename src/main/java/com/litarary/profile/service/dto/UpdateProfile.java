package com.litarary.profile.service.dto;

import com.litarary.account.domain.BookCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpdateProfile {

    private String nickName;
    private String password;
    private List<BookCategory> bookCategoryList;
}
