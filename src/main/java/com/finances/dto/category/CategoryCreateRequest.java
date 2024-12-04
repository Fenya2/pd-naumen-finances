package com.finances.dto.category;

public record CategoryCreateRequest(String name, long userId, long parentId) {
}
