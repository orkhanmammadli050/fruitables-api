package az.edu.itbrains.fruitables.controllers.admin;

import az.edu.itbrains.fruitables.dtos.category.CategoryDto;
import az.edu.itbrains.fruitables.dtos.product.ProductCreateDto;
import az.edu.itbrains.fruitables.dtos.product.ProductDashboardDto;
import az.edu.itbrains.fruitables.dtos.product.ProductUpdateDto;
import az.edu.itbrains.fruitables.services.CategoryService;
import az.edu.itbrains.fruitables.services.ProductService;
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
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> body = new HashMap<>();
        List<ProductDashboardDto> productDashboardDtoList = productService.getDashboardProducts();
        body.put("products", productDashboardDtoList);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/product/create")
    public ResponseEntity<Map<String, Object>> create() {
        Map<String, Object> body = new HashMap<>();
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        body.put("categories", categoryDtoList);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/product")
    public ResponseEntity<Void> create(@Valid @RequestBody ProductCreateDto productCreate) {
        productService.createProduct(productCreate);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Map<String, Object>> getForUpdate(@PathVariable Long id) {
        Map<String, Object> body = new HashMap<>();
        List<CategoryDto> categoryDtoList = categoryService.getAllCategories();
        body.put("categories", categoryDtoList);
        ProductUpdateDto productUpdateDto = productService.getUpdatedProduct(id);
        body.put("product", productUpdateDto);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDto productUpdate) {
        productService.updateProduct(id, productUpdate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}