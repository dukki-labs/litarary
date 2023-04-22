package com.litarary.recommend.service.change;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.recommend.domain.RecommendStatus;
import com.litarary.recommend.domain.entity.RecommendBook;
import com.litarary.recommend.repository.RecommendBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IncreamentChangeRecommend implements ChangeRecommend {

    private final RecommendStatus recommendStatus = RecommendStatus.INCREAMENT;
    private final RecommendBookRepository recommendBookRepository;
    @Override
    public int change(Member member, Book book) {
        boolean isAlreadyRecommend = recommendBookRepository.existsByMemberAndBook(member, book);
        if (isAlreadyRecommend) {
            return book.getRecommendCount();
        }
        book.updateRecommendCount(recommendStatus);
        RecommendBook recommendBook = new RecommendBook(member, book);
        recommendBookRepository.save(recommendBook);
        return book.getRecommendCount();
    }

    @Override
    public boolean isMatch(RecommendStatus recommendStatus) {
        return this.recommendStatus.equals(recommendStatus);
    }
}
