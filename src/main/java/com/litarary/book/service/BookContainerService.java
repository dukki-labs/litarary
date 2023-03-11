package com.litarary.book.service;

import com.litarary.book.service.dto.ContainerBookInfo;
import org.springframework.data.domain.Pageable;

public interface BookContainerService {
    ContainerBookInfo searchBookList(String searchKeyword, Pageable pageable);
}
