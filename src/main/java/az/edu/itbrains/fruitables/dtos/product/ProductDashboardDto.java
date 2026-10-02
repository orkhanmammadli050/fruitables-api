package az.edu.itbrains.fruitables.dtos.product;

import az.edu.itbrains.fruitables.dtos.category.CategoryDto;
import az.edu.itbrains.fruitables.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDashboardDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
    private int quantity;
    private double cashbackPercent;
    private CategoryDto category;
    private String photoUrl;

}
