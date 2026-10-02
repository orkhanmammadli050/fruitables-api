package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.category.CategoryPinnedDto;
import az.edu.itbrains.fruitables.dtos.product.*;
import az.edu.itbrains.fruitables.dtos.shop.ShopFilterDto;
import az.edu.itbrains.fruitables.helpers.SeoHelper;
import az.edu.itbrains.fruitables.models.Category;
import az.edu.itbrains.fruitables.models.Comment;
import az.edu.itbrains.fruitables.models.Photo;
import az.edu.itbrains.fruitables.models.Product;
import az.edu.itbrains.fruitables.repositories.CommentRepository;
import az.edu.itbrains.fruitables.repositories.ProductRepository;
import az.edu.itbrains.fruitables.services.CategoryService;
import az.edu.itbrains.fruitables.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CommentRepository commentRepository;

    @Override
    public boolean createProduct(ProductCreateDto productCreate) {
        Category category = categoryService.getCategoryById(productCreate.getCategoryId());
        String slug = SeoHelper.createSeoUrl(productCreate.getName());

        Product product = new Product();
        product.setName(productCreate.getName());
        product.setPrice(productCreate.getPrice());
        product.setDiscount(productCreate.getDiscount());
        product.setDescription(productCreate.getDescription());
        product.setShortDescription(productCreate.getShortDescription());
        product.setQuantity(productCreate.getQuantity());
        product.setCashbackPercent(productCreate.getCashbackPercent());
        product.setCategory(category);
        product.setSlug(slug);
        productRepository.save(product);

        return true;
    }

    @Override
    public boolean updateProduct(Long id, ProductUpdateDto productUpdate) {
        Category category = categoryService.getCategoryById(productUpdate.getCategoryId());
        String slug = SeoHelper.createSeoUrl(productUpdate.getName());

        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productUpdate.getName());
        product.setPrice(productUpdate.getPrice());
        product.setDiscount(productUpdate.getDiscount());
        product.setDescription(productUpdate.getDescription());
        product.setShortDescription(productUpdate.getShortDescription());
        product.setQuantity(productUpdate.getQuantity());
        product.setCashbackPercent(productUpdate.getCashbackPercent());
        product.setCategory(category);
        product.setSlug(slug);
        productRepository.save(product);

        return true;
    }

    @Override
    public List<ProductDashboardDto> getDashboardProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            return List.of();
        }
        List<ProductDashboardDto> productDashboardDtoList = productList.stream().map(product -> modelMapper.map(product, ProductDashboardDto.class)).toList();

        return productDashboardDtoList;
    }


    @Override
    public ProductUpdateDto getUpdatedProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        ProductUpdateDto productUpdateDto = modelMapper.map(product, ProductUpdateDto.class);
        return productUpdateDto;
    }

    @Override
    public List<ProductPinnedDto> getPinnedProducts() {
        List<ProductPinnedDto> productPinnedDtoList = productRepository.getPinnedProducts();

        return productPinnedDtoList;
    }

    @Override
    public List<ProductFeatureDto> getFeatureProducts() {

        List<Product> productList = productRepository.findTop8ByIsFeaturedTrue();

        if(!productList.isEmpty()){
            List<ProductFeatureDto> productFeatureDtoList2 = new ArrayList<>();
            for (Product product : productList) {
                ProductFeatureDto productFeatureDto = modelMapper.map(product, ProductFeatureDto.class);
                productFeatureDto.setPhotoUrl(product.getPhotos().get(0).getUrl());
                productFeatureDtoList2.add(productFeatureDto);
            }
            return  productFeatureDtoList2;
        }
        return List.of();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }


    @Override
    public List<ProductBestsellerDto> getBestsellerProducts() {
        List<Product> products = productRepository.findTop10ByOrderBySalesCountDesc();

        return products.stream().map(product -> {
            ProductBestsellerDto dto = modelMapper.map(product, ProductBestsellerDto.class);

            if (product.getPhotos() != null && !product.getPhotos().isEmpty()) {
                dto.setPhotoUrl(product.getPhotos().get(0).getUrl());
            }

            List<Comment> comments = commentRepository.findByProductId(product.getId());
            double avgRating = 0.0;
            if (comments != null && !comments.isEmpty()) {
                double sum = 0;
                for (Comment c : comments) {
                    if (c.getRating() != null) {
                        sum += c.getRating();
                    }
                }
                avgRating = Math.round((sum / comments.size()) * 10.0) / 10.0;
            }
            dto.setAverageRating(avgRating);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        if (product != null) {
            ProductDetailDto dto = modelMapper.map(product, ProductDetailDto.class);

            List<Comment> comments = commentRepository.findByProductId(product.getId());
            double avgRating = 0.0;
            int count = 0;

            if (comments != null && !comments.isEmpty()) {
                double sum = 0;
                for (Comment c : comments) {
                    if (c.getRating() != null) {
                        sum += c.getRating();
                    }
                }
                avgRating = Math.round((sum / comments.size()) * 10.0) / 10.0;
                count = comments.size();
            }

            dto.setAverageRating(avgRating);

            return dto;
        }
        return new ProductDetailDto();
    }

    @Override
    public Page<Product> getShopProducts(Long categoryId, String keyword, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {

        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        return productRepository.filterProducts(categoryId, keyword, minPrice, maxPrice, pageable);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public double calculateAverageRating(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        if (comments == null || comments.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Comment comment : comments) {
            if (comment.getRating() != null) {
                sum += comment.getRating();
            }
        }
        return Math.round((sum / comments.size()) * 10.0) / 10.0;
    }
}
