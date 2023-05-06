package com.litarary.profile.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final AccountRepository accountRepository;

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
}
