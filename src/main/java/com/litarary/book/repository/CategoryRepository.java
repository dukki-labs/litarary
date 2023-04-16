package com.litarary.book.repository;

import com.litarary.account.domain.BookCategory;
import com.litarary.account.domain.entity.Member;
import com.litarary.book.domain.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByBookCategory(BookCategory bookCategory);
    List<Category> findByBookCategoryIn(List<BookCategory> bookCategories);

    @Query("select c from Category c inner join MemberCategory mc on c.id = mc.category.id where mc.member = :member")
    List<Category> findCategoriesByMember(@Param("member") Member member);
}
