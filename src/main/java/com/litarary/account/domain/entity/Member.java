package com.litarary.account.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRole> memberRole = new ArrayList<>();
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAccountTerms;

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
}
