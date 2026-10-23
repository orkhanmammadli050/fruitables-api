package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.category.CategoryPinnedDto;
import az.edu.itbrains.fruitables.dtos.product.ProductBestsellerDto;
import az.edu.itbrains.fruitables.dtos.product.ProductFeatureDto;
import az.edu.itbrains.fruitables.dtos.product.ProductPinnedDto;
import az.edu.itbrains.fruitables.services.CategoryService;
import az.edu.itbrains.fruitables.services.CommentService;
import az.edu.itbrains.fruitables.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {

        List<CategoryPinnedDto> categoryPinnedDtoList = categoryService.getPinnedCategories();
        List<ProductPinnedDto> productPinnedDtoList = productService.getPinnedProducts();
        List<ProductFeatureDto> productFeatureDtoList = productService.getFeatureProducts();
        List<ProductBestsellerDto> bestsellers = productService.getBestsellerProducts();

        Map<String, Object> body = new HashMap<>();
        body.put("bestsellers", bestsellers);
        body.put("pinnedCategories", categoryPinnedDtoList);
        body.put("pinnedProducts", productPinnedDtoList);
        body.put("featuredProducts", productFeatureDtoList);
        body.put("testimonials", commentService.getAllComments());

        return ResponseEntity.ok(body);
    }


    @PostMapping("/comment/add")
    public ResponseEntity<Void> addComment(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String content) {

        commentService.addComment(name, email, content);
        return ResponseEntity.ok().build();
    }
}
