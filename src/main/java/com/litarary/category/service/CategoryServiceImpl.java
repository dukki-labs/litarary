package com.litarary.category.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberCategoryInfo findInterestCategory(Long memberId) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        List<Category> categoryList = categoryRepository.findCategoriesByMember(member);

        return MemberCategoryInfo.builder()
                .memberId(memberId)
                .nickName(member.getNickName())
                .categoryList(categoryList)
                .build();
    }
}
