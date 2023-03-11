package com.litarary.book.repository;

import com.litarary.book.domain.entity.CategoryMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMapRepository extends JpaRepository<CategoryMap, Long> {

    CategoryMap findByOriginalId(long originalId);
}
