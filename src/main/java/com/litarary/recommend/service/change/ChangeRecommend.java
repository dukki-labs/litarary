package com.litarary.recommend.service.change;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.recommend.domain.RecommendStatus;

public interface ChangeRecommend {

    int change(Member member, Book book);

    boolean isMatch(RecommendStatus recommendStatus);
}
