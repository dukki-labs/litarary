package com.litarary.book.repository;

import com.litarary.account.domain.BookCategory;
import com.litarary.book.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByBookCategory(BookCategory bookCategory);
}
