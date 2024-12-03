package com.finances.dto.category;

import com.finances.model.Category;
import com.finances.model.Goal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static CategoryDTO toGoal(Goal goal) {
        return new CategoryDTO(goal.getId(),
                            goal.getOwner().getId(),
                            goal.getName(),
                            goal.getAmount(),
                            goal.getDate(),
                            goal.getAccount().getId());
    }

    public static CategoryGetResponse toCategoryGetResponse(Category category, long[] idChild) {
        return new CategoryGetResponse(category.getId(),
                                                idChild,
                                                category.getName());
    }

    public static CategoryCreateResponse toCategoryCreateResponse(Category newCategory) {
        return new CategoryCreateResponse(newCategory.getId());
    }
}
