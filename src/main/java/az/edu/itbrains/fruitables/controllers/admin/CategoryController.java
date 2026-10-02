package az.edu.itbrains.fruitables.controllers.admin;

import az.edu.itbrains.fruitables.dtos.category.CategoryCreateDto;
import az.edu.itbrains.fruitables.dtos.category.CategoryDashboardDto;
import az.edu.itbrains.fruitables.dtos.category.CategoryUpdateDto;
import az.edu.itbrains.fruitables.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> body = new HashMap<>();
        List<CategoryDashboardDto> categoryDashboardDtoList = categoryService.getDashboardCategories();
        body.put("categories", categoryDashboardDtoList);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/category")
    public ResponseEntity<Void> create(@Valid @RequestBody CategoryCreateDto categoryCreate) {
        categoryService.createCategory(categoryCreate);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Map<String, Object>> getForUpdate(@PathVariable Long id) {
        Map<String, Object> body = new HashMap<>();
        CategoryUpdateDto categoryUpdateDto = categoryService.getUpdatedCategory(id);
        body.put("category", categoryUpdateDto);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDto categoryUpdate) {
        categoryService.updateCategory(id, categoryUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}