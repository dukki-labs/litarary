package com.litarary.recommend.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.repository.BookRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.recommend.domain.RecommendStatus;
import com.litarary.recommend.repository.RecommendBookRepository;
import com.litarary.recommend.service.change.ChangeRecommend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendServiceImpl implements RecommendService {

    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;
    private final List<ChangeRecommend> changeRecommends;

    @Override
    @Transactional
    public int updateRecommendCount(Long memberId, Long bookId, RecommendStatus recommendStatus) {
        final Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.BOOK_NOT_FOUND));

        return changeRecommends.stream()
                .filter(changeRecommend -> changeRecommend.isMatch(recommendStatus))
                .mapToInt(changeRecommend -> changeRecommend.change(member, book))
                .findFirst()
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.FAIL_UPDATE_RECOMMEND_COUNT));
    }
}
