package com.litarary.account.domain.entity;

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

    @Column(nullable = false)
    private String nickName;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MemberRole> memberRole = new ArrayList<>();
    @Column(nullable = false)
    private String password;

    @Column
    private String authCode;

    @Column(nullable = false)
    private boolean isServiceTerms;

    @Column(nullable = false)
    private boolean isPrivacyTerms;

    @Column(nullable = false)
    private boolean isServiceAlarm;

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
}
