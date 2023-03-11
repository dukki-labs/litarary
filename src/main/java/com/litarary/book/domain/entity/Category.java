package com.litarary.book.domain.entity;

import com.litarary.account.domain.BookCategory;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private BookCategory bookCategory;
}
