package com.litarary.book.service;

import com.litarary.book.service.dto.ContainerBookInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookContainerService bookContainerService;
    @Transactional(readOnly = true)
    public ContainerBookInfo searchBookListByContainer(String searchKeyword, Pageable pageable) {
        return bookContainerService.searchBookList(searchKeyword, pageable);
    }
}
