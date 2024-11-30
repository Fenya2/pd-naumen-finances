package com.finances.model.category;

import java.util.Objects;

import com.finances.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Категория финансовой операции (доход/расход)
 */
@Data
@Entity
@Table(name = "tbl_category")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category_type")
public abstract class Category
{
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
