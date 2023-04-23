package com.litarary.book.repository;

import com.litarary.book.service.dto.BookContent;
import com.litarary.book.service.dto.RentalBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Mapper
public interface BookMybatisRepository {

    List<BookContent> findByRentalBookList(@Param("companyId") Long companyId,
                                           @Param("rentalBook") RentalBook rentalBook,
                                           @Param("offset") long offset);

    int findByRentalBookCount(@Param("companyId") Long companyId,
                              @Param("rentalBook") RentalBook rentalBook);

    List<BookContent> findBySearchBookList(@Param("companyId") Long companyId,
                                    @Param("searchWord") String searchWord,
                                    @Param("pageRequest") PageRequest pageRequest);

    int findBySearchBookCount(@Param("companyId") Long companyId,
                              @Param("searchWord") String searchWord);
}
