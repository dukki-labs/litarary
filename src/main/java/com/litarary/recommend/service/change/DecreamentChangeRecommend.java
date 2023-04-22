package com.litarary.recommend.service.change;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import com.litarary.recommend.domain.RecommendStatus;
import com.litarary.recommend.domain.entity.RecommendBook;
import com.litarary.recommend.repository.RecommendBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DecreamentChangeRecommend implements ChangeRecommend {

    private final RecommendStatus recommendStatus = RecommendStatus.DECREAMENT;
    private final RecommendBookRepository recommendBookRepository;
    @Override
    public int change(Member member, Book book) {
        book.updateRecommendCount(recommendStatus);
        RecommendBook recommendBook = recommendBookRepository.findByMemberAndBook(member, book)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.RECOMMEND_BOOK_NOT_FOUND));
        recommendBookRepository.delete(recommendBook);
        return book.getRecommendCount();
    }

    @Override
    public boolean isMatch(RecommendStatus recommendStatus) {
        return this.recommendStatus.equals(recommendStatus);
    }
}
