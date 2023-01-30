package com.litarary.account.controller.dto;

import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.InterestType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class MemberDto {

    @Getter
    public static class Request {
        @NotBlank
        private String nickName;

        @Email
        private String email;

        @NotBlank
        private String password;

        private List<AccessRole> accessRoles;

        @NotNull
        @AssertTrue
        private boolean accountTerms;

        @NotNull
        @AssertTrue
        private boolean serviceTerms;

        @NotNull
        @AssertTrue
        private boolean privacyTerms;

        @NotNull
        private boolean serviceAlarm;

        private List<InterestType> interestItems;
    }
}
