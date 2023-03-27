package com.litarary.account.controller.dto;

import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.BookCategory;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request {

        @Min(1)
        private long memberId;
        @NotBlank(message = "닉네임은 필수 입력값 입니다.")
        @Size(min = 4)
        private String nickName;

        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일이 잘못 입력되었어요!")
        private String email;

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "비밀번호는 8~16글자이며 1개 이상의 영문, 숫자, 특수문자를 이용해주세요.")
        @NotBlank(message = "패스워드는 필수 입력값 입니다.")
        private String password;

        @NotEmpty(message = "가입하고 싶은 역할을 입력해주세요.")
        private List<AccessRole> accessRoles;

        @NotNull(message = "서비스 약관 동의는 필수입니다.")
        @AssertTrue
        private boolean serviceTerms;

        @NotNull(message = "개인정보 수집 동의는 필수입니다.")
        @AssertTrue
        private boolean privacyTerms;

        @NotNull(message = "서비스 안내 동의는 선택입니다.")
        private boolean serviceAlarm;

        @Size(min = 1, max = 4, message = "관심사 카테고리는 최대 4개까지 선택 가능합니다.")
        private List<BookCategory> bookCategoryList;
    }
}
