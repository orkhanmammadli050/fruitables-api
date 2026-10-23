package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.dtos.product.ProductDetailDto;
import az.edu.itbrains.fruitables.dtos.product.ProductPinnedDto;
import az.edu.itbrains.fruitables.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM product_pinned_list")
    List<ProductPinnedDto> getPinnedProducts();

    List<Product> findTop8ByIsFeaturedTrue();

    Product findBySlug(String slug);

    List<Product> findTop10ByOrderBySalesCountDesc();

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> filterProducts(@Param("categoryId") Long categoryId,
                                 @Param("keyword") String keyword,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice,
                                 Pageable pageable);
}
