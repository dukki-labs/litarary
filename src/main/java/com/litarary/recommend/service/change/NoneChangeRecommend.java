package com.litarary.recommend.service.change;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.recommend.domain.RecommendStatus;
import org.springframework.stereotype.Component;

@Component
public class NoneChangeRecommend implements ChangeRecommend {

    private final RecommendStatus recommendStatus = RecommendStatus.NONE;
    @Override
    public int change(Member member, Book book) {
        book.updateRecommendCount(recommendStatus);
        return book.getRecommendCount();
    }

    @Override
    public boolean isMatch(RecommendStatus recommendStatus) {
        return this.recommendStatus.equals(recommendStatus);
    }
}
