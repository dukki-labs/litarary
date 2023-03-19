package com.litarary.account.domain.entity;

import com.litarary.account.domain.AccessRole;
import com.litarary.account.domain.UseYn;
import com.litarary.book.domain.entity.Category;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MemberRole> memberRole = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberCategory> memberCategoryList = new ArrayList<>();

    private String password;

    private String authCode;

    private boolean isServiceTerms;

    private boolean isPrivacyTerms;

    private boolean isServiceAlarm;

    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    @Column(nullable = false)
    @CreatedDate
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private OffsetDateTime updatedAt;

    private String refreshToken;

    public void updatePasswordEncode(String password) {
        this.password = password;
    }

    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }

    public void validAuthCode(String accessCode) {
        if (!accessCode.equals(this.authCode)) {
            throw new LitararyErrorException(ErrorCode.MISS_MATCH_AUTH_CODE);
        }
    }

    public void updateAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void isSameAuthCode(String authCode) {
        if (!this.authCode.equals(authCode)) {
            throw new LitararyErrorException(ErrorCode.MISS_MATCH_AUTH_CODE);
        }
    }

    public void updateCategories(List<Category> categories) {
        if (this.memberCategoryList == null) {
            this.memberCategoryList = new ArrayList<>();
        }
        this.memberCategoryList.clear();
        List<MemberCategory> memberCategories = categories.stream()
                .map(category -> new MemberCategory(this, category))
                .toList();
        this.memberCategoryList.addAll(memberCategories);
    }

    public void updateAccountSignUp(Member updateMember) {
        this.nickName = updateMember.nickName;
        this.email = updateMember.email;
        this.isServiceTerms = updateMember.isServiceTerms;
        this.isPrivacyTerms = updateMember.isPrivacyTerms;
        this.isServiceAlarm = updateMember.isServiceAlarm;
        this.useYn = updateMember.useYn;
        this.updatedAt = updateMember.updatedAt;
    }

    public void updateMemberRole(List<AccessRole> accessRoleList) {
        if (this.memberRole == null) {
            this.memberRole = new ArrayList<>();
        }
        this.memberRole.clear();
        List<MemberRole> memberRoles = accessRoleList.stream()
              .map(role -> new MemberRole(this, role))
              .toList();
        this.memberRole.addAll(memberRoles);
    }
}
