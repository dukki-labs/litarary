package com.litarary.book.controller.mapper;

import com.litarary.book.controller.dto.BookRegistrationDto;
import com.litarary.book.controller.dto.ContainerBookInfoDto;
import com.litarary.book.service.dto.ContainerBookInfo;
import com.litarary.book.service.dto.RegisterBook;
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
}
