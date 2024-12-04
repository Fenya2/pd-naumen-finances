package com.finances.dto.category;

import com.finances.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static CategoryGetResponse toCategoryGetResponse(Category category, long[] idChild) {
        return new CategoryGetResponse(category.getId(),
                                                idChild,
                                                category.getName());
    }

    public static CategoryCreateResponse toCategoryCreateResponse(Category newCategory) {
        return new CategoryCreateResponse(newCategory.getId());
    }
}
