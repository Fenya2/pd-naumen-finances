package com.finances.dto.category.view;

import java.util.ArrayList;
import java.util.List;

public class CategoryNode {
    private final Long id;
    private final String name;
    private final List<CategoryNode> children = new ArrayList<>();

    // Конструкторы
    public CategoryNode(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryNode> getChildren() {
        return children;
    }
}

