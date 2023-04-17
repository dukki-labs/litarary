package com.litarary.book.repository;

import com.litarary.book.service.dto.RentalBook;
import com.litarary.book.service.dto.BookContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMybatisRepository {

    List<BookContent> findByRentalBookList(@Param("companyId") Long companyId,
                                           @Param("rentalBook") RentalBook rentalBook,
                                           @Param("offset") long offset);

    int findByRentalBookCount(@Param("companyId") Long companyId,
                              @Param("rentalBook") RentalBook rentalBook);
}
