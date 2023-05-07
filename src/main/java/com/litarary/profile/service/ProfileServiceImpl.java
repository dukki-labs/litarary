package com.litarary.profile.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.BookRepository;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.category.service.dto.PageBookInfo;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.domain.RegisterDateCalculate;
import com.litarary.profile.service.dto.CategoryInfo;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final List<RegisterDateCalculate> registerDateCalculates;

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public PageBookInfo registerBooks(Long memberId, PageRequest pageRequest, RegisterDate registerDate) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        LocalDateTime startSearchDateTime = registerDateCalculates.stream()
                .filter(item -> item.matchType(registerDate))
                .map(item -> item.calculateStartDateTime())
                .findFirst()
                .orElse(null);

        Page<Book> registerBooks = bookRepository.findByMemberRegisterBooks(member.getId(), startSearchDateTime, pageRequest);
        final int pageNumber = pageRequest.getPageNumber();
        final int pageSize = pageRequest.getPageSize();

        return PageBookInfo.of(pageNumber, pageSize, registerBooks);
    }

    @Override
    public void deleteRegisterBook(Long memberId, Long bookId) {
        accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        bookRepository.deleteRegisterBook(bookId);
    }
}
