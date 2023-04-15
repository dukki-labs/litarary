package com.litarary.book.controller.mapper;

import com.litarary.book.controller.dto.*;
import com.litarary.book.service.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public ContainerBookInfoDto.Response toContainerBookInfoDto(ContainerBookInfo containerBookInfo) {
        List<ContainerBookInfoDto.ContainerBookDto> containerBookDtos = containerBookInfo.getBookList()
                .stream()
                .map(item ->
                        ContainerBookInfoDto.ContainerBookDto
                                .builder()
                                .title(item.getTitle())
                                .author(item.getAuthor())
                                .pubDate(item.getPubDate())
                                .description(item.getDescription())
                                .imageUrl(item.getImageUrl())
                                .categoryMapId(item.getCategoryMapId())
                                .bookCategory(item.getBookCategory())
                                .publisher(item.getPublisher())
                                .build()
                ).toList();

        return ContainerBookInfoDto.Response
                .builder()
                .page(containerBookInfo.getPage())
                .size(containerBookInfo.getSize())
                .totalCount(containerBookInfo.getTotalCount())
                .bookList(containerBookDtos)
                .build();
    }

    public RegisterBook toRegisterBook(BookRegistrationDto.Request request) {
        return RegisterBook.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .review(request.getReview())
                .deadLine(request.getDeadLine())
                .imageUrl(request.getImageUrl())
                .returnLocation(request.getReturnLocation())
                .content(request.getContent())
                .publisher(request.getPublisher())
                .publishDate(request.getPublishDate())
                .memberId(request.getMemberId())
                .build();
    }

    public List<BookInfoDto> toBookInfoDto(List<BookInfo> recentBookList) {
        return recentBookList.stream()
                .map(book ->
                        BookInfoDto.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .categoryId(book.getCategoryId())
                                .category(book.getCategory())
                                .imageUrl(book.getImageUrl())
                                .content(book.getContent())
                                .review(book.getReview())
                                .author(book.getAuthor())
                                .publisher(book.getPublisher())
                                .publishDate(book.getPublishDate())
                                .deadLine(book.getDeadLine())
                                .recommendCount(book.getRecommendCount())
                                .returnLocation(book.getReturnLocation())
                                .regDt(book.getCreatedAt())
                                .newTag(book.getNewTag())
                                .build()
                ).toList();
    }

    public ReturnBook.Request toReturnBook(BookReturnDto.Request request) {
        return new ReturnBook.Request(request.getRentalReview(), request.getRecommend());
    }

    public RentalBook toRentalBook(RentalBookDto.Request request) {
        final int defaultMinusPage = 1;
        return RentalBook.builder()
                .searchType(request.getSearchType())
                .bookCategory(request.getBookCategory())
                .searchKeyword(request.getSearchKeyword())
                .page(request.getPage() - defaultMinusPage)
                .size(request.getSize())
                .build();
    }
}
