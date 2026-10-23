package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.product.ProductDetailDto;
import az.edu.itbrains.fruitables.dtos.product.ProductDto;
import az.edu.itbrains.fruitables.models.Product;
import az.edu.itbrains.fruitables.services.CategoryService;
import az.edu.itbrains.fruitables.services.CommentService;
import az.edu.itbrains.fruitables.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @GetMapping("/product/{category}/{slug}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable String slug) {

        ProductDetailDto productDetailDto = productService.getProductBySlug(slug);

        var comments = commentService.getCommentsByProductId(productDetailDto.getId());

        double averageRating = productService.calculateAverageRating(productDetailDto.getId());

        Map<String, Object> body = new HashMap<>();
        body.put("featuredProducts", productService.getFeatureProducts());
        body.put("product", productDetailDto);
        body.put("productComments", comments);
        body.put("averageRating", averageRating);

        return ResponseEntity.ok(body);
    }

    @PostMapping("/product/comment/add/{id}")
    public ResponseEntity<Void> addProductComment(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String content,
            @RequestParam int rating) {

        commentService.addComment(id, name, email, content, rating);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/shop")
    public ResponseEntity<Map<String, Object>> shop(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productService.getShopProducts(categoryId, keyword, minPrice, maxPrice, pageable);
        Page<ProductDto> productDtoPage = productPage.map(this::toProductDto);

        Map<String, Object> body = new HashMap<>();
        body.put("products", productDtoPage.getContent());
        body.put("currentPage", page);
        body.put("totalPages", productDtoPage.getTotalPages());
        body.put("totalItems", productDtoPage.getTotalElements());

        body.put("categories", categoryService.getAllCategories());
        body.put("selectedCategoryId", categoryId);
        body.put("selectedKeyword", keyword);
        body.put("selectedMinPrice", minPrice);
        body.put("selectedMaxPrice", maxPrice);

        return ResponseEntity.ok(body);
    }

    private ProductDto toProductDto(Product product) {
        String photoUrl = product.getPhotos().isEmpty() ? null : product.getPhotos().get(0).getUrl();

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .photoUrl(photoUrl)
                .build();
    }
}