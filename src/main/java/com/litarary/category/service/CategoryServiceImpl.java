package com.litarary.category.service;

import com.litarary.account.domain.entity.Company;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.BookRepository;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.category.service.dto.BookInCategoryInfoRequest;
import com.litarary.category.service.dto.MemberCategoryInfo;
import com.litarary.category.service.dto.PageBookInfo;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

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

    @Override
    @Transactional(readOnly = true)
    public PageBookInfo findCategoryInBooks(BookInCategoryInfoRequest requestInfo) {
        final long memberId = requestInfo.getMemberId();
        final int page = requestInfo.getPage();
        final int size = requestInfo.getSize();
        final Pageable pageable = PageRequest.of(page, size);
        final Category category = categoryRepository.findById(requestInfo.getCategoryId())
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_ALLOWED_CATEGORIES));

        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        final Company company = member.getCompany();

        Page<Book> bookList = bookRepository.findByCategoryInBookList(company, category, pageable);
        return PageBookInfo.of(page, size, bookList);
    }
}
