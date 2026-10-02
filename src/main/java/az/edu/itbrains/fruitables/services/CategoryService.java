package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.category.*;
import az.edu.itbrains.fruitables.models.Category;

import java.util.List;

public interface CategoryService {
    boolean createCategory(CategoryCreateDto categoryCreate);
    boolean updateCategory(Long id,CategoryUpdateDto categoryUpdate);
    List<CategoryDashboardDto> getDashboardCategories();

    CategoryUpdateDto getUpdatedCategory(Long id);

    Category getCategoryById(Long categoryId);

    List<CategoryDto> getAllCategories();

    List<CategoryPinnedDto> getPinnedCategories();

    void deleteCategory(Long id);
}
