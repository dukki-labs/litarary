package com.litarary.alarm.service;

import com.litarary.account.domain.entity.Member;
import com.litarary.account.repository.AccountRepository;
import com.litarary.alarm.domain.AcceptBookInfo;
import com.litarary.alarm.domain.AcceptState;
import com.litarary.alarm.domain.AlarmBookInfo;
import com.litarary.alarm.repository.AlarmRepository;
import com.litarary.book.domain.RentalState;
import com.litarary.book.domain.RentalUseYn;
import com.litarary.book.domain.entity.Book;
import com.litarary.book.domain.entity.BookRental;
import com.litarary.book.repository.BookRentalRepository;
import com.litarary.book.repository.BookRepository;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.LitararyErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AccountRepository accountRepository;
    private final BookRentalRepository bookRentalRepository;
    private final BookRepository bookRepository;


    public List<AlarmBookInfo> bookRentalTargetList(Long memberId) {
        return alarmRepository.rentalBookHistoryList(memberId);
    }

    @Transactional
    public void acceptBookRental(AcceptBookInfo acceptBookInfo) {
        AcceptState acceptState = acceptBookInfo.getAcceptState();
        final long memberId = acceptBookInfo.getMemberId();
        final long bookId = acceptBookInfo.getBookId();
        if (AcceptState.ACCEPT.equals(acceptState)) {
            BookRental bookRental = bookRentalRepository.findByMemberIdAndRentalState(memberId, RentalState.REQUEST)
                    .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_RENTAL_REQUEST));

            bookRental.updateRentalState(RentalState.RENTAL);
            return;
        }

        Member member = accountRepository.findById(memberId)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.MEMBER_NOT_FOUND));

        Book book = bookRepository.findByIdAndRentalUseYnAndCompany(bookId, RentalUseYn.N, member.getCompany())
                .orElse(null);
        if (Objects.isNull(book)) return;

        book.updateRentalUseYn(RentalUseYn.Y);

        BookRental bookRental = bookRentalRepository.findByMemberIdAndRentalState(memberId, RentalState.REQUEST)
                .orElseThrow(() -> new LitararyErrorException(ErrorCode.NOT_RENTAL_REQUEST));
        bookRentalRepository.delete(bookRental);
    }
}
