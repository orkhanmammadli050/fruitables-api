package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.product.*;
import az.edu.itbrains.fruitables.dtos.product.ProductCreateDto;
import az.edu.itbrains.fruitables.dtos.product.ProductUpdateDto;
import az.edu.itbrains.fruitables.dtos.shop.ShopFilterDto;
import az.edu.itbrains.fruitables.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    boolean createProduct(ProductCreateDto productCreate);
    boolean updateProduct(Long id, ProductUpdateDto productUpdate);
    List<ProductDashboardDto> getDashboardProducts();

    ProductUpdateDto getUpdatedProduct(Long id);

    List<ProductPinnedDto> getPinnedProducts();

    List<ProductFeatureDto> getFeatureProducts();

    ProductDetailDto getProductBySlug(String slug);

    Product getProductById(Long productId);

    List<ProductBestsellerDto> getBestsellerProducts();

    Page<Product> getShopProducts(Long categoryId, String keyword, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    void deleteProduct(Long id);

    double calculateAverageRating(Long productId);
}
