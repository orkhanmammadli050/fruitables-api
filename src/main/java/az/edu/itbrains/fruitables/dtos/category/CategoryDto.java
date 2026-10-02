package az.edu.itbrains.fruitables.dtos.category;

import az.edu.itbrains.fruitables.dtos.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String slug;
    private List<ProductDto> products;
}
