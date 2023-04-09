package com.litarary.account.service;

import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Company;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.account.service.dto.LoginInfo;
import com.litarary.account.service.dto.MemberDefaultInfo;
import com.litarary.account.service.dto.RefreshTokenInfo;
import com.litarary.account.service.dto.SignUpMemberInfo;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.utils.jwt.JwtTokenProvider;
import com.litarary.utils.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CompanyService companyService;

    public void signUpMember(SignUpMemberInfo memberInfo) {
        Member updateMember = memberInfo.getMember();
        validDuplicatedEmail(updateMember.getEmail(), UseYn.Y);

        Member member = accountRepository.findById(memberInfo.getMemberId())
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        member.updatePasswordEncode(passwordEncoder.encode(updateMember.getPassword()));
        member.updateAccountSignUp(updateMember);
        member.updateMemberRole(memberInfo.getAccessRoles());

        List<Category> categories = categoryRepository.findByBookCategoryIn(memberInfo.getBookCategoryList());
        member.updateCategories(categories);

        Company company = companyService.findCompany(member.getEmail());
        member.updateCompany(company);
    }

    public LoginInfo login(String email, String password) {
        Member member = getMemberByEmail(email, UseYn.Y);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new LitararyErrorException(ErrorCode.MISS_MATCH_PASSWORD);
        }

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        //CustomUserDetailsService 가 실행됨
        Authentication authenticate = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authenticate);
        member.updateRefreshToken(tokenInfo.getRefreshToken());

        return LoginInfo.builder()
                .tokenInfo(tokenInfo)
                .member(member)
                .build();
    }


    public RefreshTokenInfo refreshToken(String email, String refreshToken) {
        Member member = getMemberByEmail(email, UseYn.Y);
        //Todo: refreshToken이 일치하는지 체크할 것.
        TokenInfo tokenInfo = jwtTokenProvider.refreshAccessToken(refreshToken);
        member.updateRefreshToken(tokenInfo.getRefreshToken());

        return RefreshTokenInfo.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .accessToken(tokenInfo.getAccessToken())
                .build();
    }

    public void updateAuthCode(long memberId, String accessCode) {
        Member member = getMemberById(memberId);

        member.updateAuthCode(accessCode);
    }

    @Transactional(readOnly = true)
    public void validDuplicatedEmail(String email, UseYn useYn) {
        boolean isDuplicatedEmail = accountRepository.existsByEmailAndUseYn(email, useYn);
        if (isDuplicatedEmail) {
            throw new LitararyErrorException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Transactional(readOnly = true)
    public Member findMember(String email, UseYn useYn) {
        return getMemberByEmail(email, useYn);
    }

    public void updatePassword(long memberId, String authCode, String password) {
        Member member = getMemberById(memberId);

        member.validAuthCode(authCode);
        member.updatePasswordEncode(passwordEncoder.encode(password));
    }

    @Transactional(readOnly = true)
    public void checkAuthCode(long memberId, String authCode) {
        Member member = getMemberById(memberId);
        member.isSameAuthCode(authCode);
    }

    public MemberDefaultInfo createMember(String email, String authCode) {
        Member findMember = accountRepository.findByEmail(email);
        if (findMember != null) {
            findMember.updateAuthCode(authCode);
            return MemberDefaultInfo.builder()
                    .memberId(findMember.getId())
                    .email(findMember.getEmail())
                    .build();
        }

        Member member = Member.builder()
                .email(email)
                .authCode(authCode)
                .useYn(UseYn.N)
                .build();

        accountRepository.findByEmail(email);
        Member savedMember = accountRepository.save(member);
        return MemberDefaultInfo.builder()
                .memberId(savedMember.getId())
                .email(savedMember.getEmail())
                .build();
    }

    private Member getMemberById(long memberId) {
        return accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Member getMemberByEmail(String email, UseYn useYn) {
        return accountRepository.findByEmailAndUseYn(email, useYn)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ACCOUNT_NOT_FOUND_EMAIL));
    }
}
