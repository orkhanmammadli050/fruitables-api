package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.category.*;
import az.edu.itbrains.fruitables.helpers.SeoHelper;
import az.edu.itbrains.fruitables.models.Category;
import az.edu.itbrains.fruitables.repositories.CategoryRepository;
import az.edu.itbrains.fruitables.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public boolean createCategory(CategoryCreateDto categoryCreate) {

        try {
            Category category = new Category();
            category.setName(categoryCreate.getCategoryName());
            category.setSlug(SeoHelper.createSeoUrl(categoryCreate.getCategoryName()));

            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateCategory(Long id,CategoryUpdateDto categoryUpdate) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow();
            category.setName(categoryUpdate.getName());
            category.setSlug(SeoHelper.createSeoUrl(categoryUpdate.getName()));
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<CategoryDashboardDto> getDashboardCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()) {
            return List.of();
        }
        List<CategoryDashboardDto> categoryDashboardDtoList = categoryList
                .stream().map(category -> modelMapper.map(category, CategoryDashboardDto.class)).toList();
        return categoryDashboardDtoList;
    }

    @Override
    public CategoryUpdateDto getUpdatedCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryUpdateDto categoryUpdateDto = modelMapper.map(category, CategoryUpdateDto.class);

        return categoryUpdateDto;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        if (!categoryList.isEmpty()) {
            return categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
        }
        return List.of();
    }

    @Override
    public List<CategoryPinnedDto> getPinnedCategories() {
        List<Category> categoryList = categoryRepository.findByIsPinnedTrue();
        if (!categoryList.isEmpty()) {
            List<CategoryPinnedDto> categoryPinnedDtoList = categoryList.stream().map(category -> modelMapper.map(category, CategoryPinnedDto.class)).toList();
            return categoryPinnedDtoList;
        }
        return List.of();
    }

    @Override
    public void deleteCategory(Long id) {

        categoryRepository.deleteById(id);
    }
}
