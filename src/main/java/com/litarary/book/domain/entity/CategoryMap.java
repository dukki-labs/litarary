package com.litarary.book.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(indexes = @Index(columnList = "originalId"))
public class CategoryMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private long originalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
