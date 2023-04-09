package com.litarary.book.service.dto;

import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.domain.entity.DeadLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class ReturnBook {

    @Getter
    @AllArgsConstructor
    public static class Request {
        private String rentalReview;
        private int recommend;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long memberId;
        private String providerNickName;
        private String bookUrl;
        private String title;
        private String publisher;
        private Category category;
        private String content;
        private DeadLine deadLine;
        private String returnLocation;
        private String review;
        private NewTag newTag;

        public Response(Book book) {
            final Member member = book.getMember();
            final LocalDateTime createdAt = book.getCreatedAt();

            providerNickName = member.getNickName();
            memberId = member.getId();
            bookUrl = book.getImageUrl();
            title = book.getTitle();
            publisher = book.getPublisher();
            category = book.getCategory();
            content = book.getContent();
            deadLine = book.getDeadLine();
            returnLocation = book.getReturnLocation();
            review = book.getReview();
            newTag = NewTag.isNewTag(createdAt);
        }
    }
}
