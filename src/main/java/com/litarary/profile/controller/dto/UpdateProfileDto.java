package com.litarary.profile.controller.dto;

import com.litarary.account.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateProfileDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @Size(min = 4)
        private String nickName;
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "비밀번호는 8~16글자이며 1개 이상의 영문, 숫자, 특수문자를 이용해주세요.")
        private String password;
        @Size(min = 1, max = 4, message = "관심사 카테고리는 최대 4개까지 선택 가능합니다.")
        @NotNull
        private List<BookCategory> bookCategoryList;
    }
}
