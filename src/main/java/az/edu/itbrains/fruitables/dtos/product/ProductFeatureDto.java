package az.edu.itbrains.fruitables.dtos.product;

import az.edu.itbrains.fruitables.dtos.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFeatureDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String photoUrl;

    private String slug;
    private CategoryDto category;
}
