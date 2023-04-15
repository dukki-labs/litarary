package com.litarary.book.repository;

import com.litarary.book.service.dto.RentalBook;
import com.litarary.book.service.dto.RentalBookResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMybatisRepository {

    List<RentalBookResponse> findByRentalBookList(@Param("companyId") Long companyId,
                                                  @Param("rentalBook") RentalBook rentalBook);
}
