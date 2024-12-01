package com.finances.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Категория финансовой операции (доход/расход)
 */
@Data
@Entity
@Table(name = "tbl_category")
public class Category
{
    public Category() {}

    public Category(String name, User owner, @Nullable Category parentCategory) {
        this.name = name;
        this.owner = owner;
        this.parent = parentCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Родительская категория (в которую данная категория вложена)
     */
    @ManyToOne(targetEntity = Category.class)
    private Category parent;

    @ManyToOne(targetEntity = User.class)
    private User owner;

    @Column(nullable = false)
    private String name;

    private boolean defaultCategory;

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Category category))
            return false;
        return id == category.id;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }
}
