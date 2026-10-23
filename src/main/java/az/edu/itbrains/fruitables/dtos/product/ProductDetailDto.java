package az.edu.itbrains.fruitables.dtos.product;

import az.edu.itbrains.fruitables.dtos.category.CategoryDto;
import az.edu.itbrains.fruitables.dtos.photo.PhotoDto;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductDetailDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal discount;

    private CategoryDto category;

    private double averageRating;

    private List<PhotoDto> photos;
}
