package com.litarary.book.service;

import com.litarary.account.domain.entity.Company;
import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.BookRental;
import com.litarary.book.domain.entity.RentalReview;
import com.litarary.book.repository.*;
import com.litarary.book.domain.entity.Category;
import com.litarary.book.service.dto.*;
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
    private final BookMybatisRepository bookMybatisRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final BookRentalRepository bookRentalRepository;
    private final RentalReviewRepository rentalReviewRepository;

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

    @Transactional(readOnly = true)
    public List<BookInfo> recentBookList(long memberId, int size) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        List<Book> bookList = bookRepository.findByRecentBookList(member.getCompany(), PageRequest.of(0, size));
        return bookList.stream()
                .map(BookInfo::of)
                .toList();
    }

    public void rentalBook(Long memberId, Long bookId) {
        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        validateBookRental(member);

        Book book = bookRepository.findByIdAndRentalUseYnAndCompany(bookId, RentalUseYn.Y, member.getCompany())
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.ALREADY_RENTAL_USER));

        book.updateRentalUseYn(RentalUseYn.N);
        BookRental defaultRental = BookRental.createDefaultRental(member, book);
        bookRentalRepository.save(defaultRental);
    }

    public void returnBook(Long memberId, ReturnBook.Request returnBook) {
        BookRental bookRental = bookRentalRepository.findByMemberIdAndReturnDateTimeIsNull(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_RENTAL_BOOK));

        Book book = bookRental.getBook();
        createRentalReview(returnBook.getRentalReview(), book);
        book.updateRentalUseYn(RentalUseYn.Y);
        book.updateRecommendCount(returnBook.getRecommend());
        bookRental.updateReturnDateTime();
    }

    @Transactional(readOnly = true)
    public ReturnBook.Response findBookReturn(Long memberId) {
        final BookRental bookRental = bookRentalRepository.findByMemberIdAndReturnDateTimeIsNull(memberId)
                .orElse(null);
        if (bookRental == null) {
            return new ReturnBook.Response();
        }

        final Book book = bookRental.getBook();
        return new ReturnBook.Response(book);
    }

    @Transactional(readOnly = true)
    public List<RentalBookResponse> findRentalBookList(Long memberId, RentalBook rentalBook) {
        final Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));
        final Company company = member.getCompany();

        return bookMybatisRepository.findByRentalBookList(company.getId(), rentalBook);
    }

    private void createRentalReview(String rentalReview, Book book) {
        if (rentalReview.length() > 0) {
            rentalReviewRepository.save(RentalReview.createRentalReview(rentalReview, book));
        }
    }

    private void validateBookRental(Member member) {
        BookRental bookRental = bookRentalRepository.findByMemberIdAndReturnDateTimeIsNull(member.getId())
                .orElse(null);
        if (bookRental != null) {
            throw new LitararyErrorException(ErrorCode.ALREADY_RENTAL_BOOK);
        }
    }
}
