package com.litarary.book.service;

import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.BookRepository;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.book.service.dto.ContainerBookInfo;
import com.litarary.book.service.dto.RegisterBook;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {

    private final BookContainerService bookContainerService;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    @Transactional(readOnly = true)
    public ContainerBookInfo searchBookListByContainer(String searchKeyword, Pageable pageable) {
        return bookContainerService.searchBookList(searchKeyword, pageable);
    }

    public void registerBook(Long categoryId, RegisterBook registerBook) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_ALLOWED_CATEGORIES));
        bookRepository.save(new Book(category, registerBook));
    }
}
