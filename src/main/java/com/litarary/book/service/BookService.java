package com.litarary.book.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.BookRental;
import com.litarary.book.domain.entity.BookRentalRepository;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.repository.BookRepository;
import com.litarary.book.repository.CategoryRepository;
import com.litarary.book.service.dto.BookInfo;
import com.litarary.book.service.dto.ContainerBookInfo;
import com.litarary.book.service.dto.RegisterBook;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {

    private final BookContainerService bookContainerService;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final BookRentalRepository bookRentalRepository;

    @Transactional(readOnly = true)
    public ContainerBookInfo searchBookListByContainer(String searchKeyword, Pageable pageable) {
        return bookContainerService.searchBookList(searchKeyword, pageable);
    }

    public void registerBook(Long categoryId, RegisterBook registerBook) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_ALLOWED_CATEGORIES));

        Member member = accountRepository.findById(registerBook.getMemberId())
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        bookRepository.save(new Book(member, category, registerBook));
    }

    public List<BookInfo> recentBookList(long memberId, int size) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        List<Book> bookList = bookRepository.findByRecentBookList(member.getCompany(), PageRequest.of(0, size));
        return bookList.stream()
                .map(BookInfo::of)
                .toList();
    }

    @Transactional
    public void rentalBook(Long memberId, Long bookId) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        Book book = bookRepository.findByIdAndRentalUseYnAndCompany(bookId, RentalUseYn.Y, member.getCompany())
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.RENTAL_NOT_FOUND_BOOK));

        book.updateRentalUseYn(RentalUseYn.N);
        BookRental defaultRental = BookRental.createDefaultRental(member, book);
        bookRentalRepository.save(defaultRental);
    }
}
