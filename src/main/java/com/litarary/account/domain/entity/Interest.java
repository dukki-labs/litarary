package com.litarary.account.domain.entity;

import com.litarary.account.domain.InterestType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private InterestType interestType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    public void updateMember(Member member) {
        this.memberId = member;
    }

    public void updateTypeAndMember(InterestType interestType, Member member) {
        this.interestType = interestType;
        this.memberId = member;
    }
}
