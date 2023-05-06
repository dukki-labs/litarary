package com.litarary.profile.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public MemberProfileDto findUserProfile(Long memberId) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        List<CategoryInfo> categoryInfos = member.getMemberCategoryList()
                .stream()
                .map(item -> item.getCategory())
                .map(item -> new CategoryInfo(item.getId(), item.getBookCategory()))
                .toList();

        return MemberProfileDto.builder()
                .nickName(member.getNickName())
                .email(member.getEmail())
                .categoryInfoList(categoryInfos)
                .build();
    }

    @Override
    public void updateProfile(Long memberId, UpdateProfile updateProfile) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        boolean isDuplicatedNickname = accountRepository.existsByNickName(updateProfile.getNickName());
        if (isDuplicatedNickname) {
            throw new LitararyErrorException(ErrorCode.DUPLICATED_NICKNAME);
        }

        List<Category> categoryList = categoryRepository.findByBookCategoryIn(updateProfile.getBookCategoryList());
        member.updatePasswordEncode(passwordEncoder.encode(updateProfile.getPassword()));
        member.updateCategories(categoryList);
        member.updateNickName(updateProfile.getNickName());
    }
}
