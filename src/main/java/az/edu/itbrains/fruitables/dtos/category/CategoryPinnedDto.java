package az.edu.itbrains.fruitables.dtos.category;

import az.edu.itbrains.fruitables.dtos.product.ProductPinnedDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPinnedDto {
    private Long id;
    private String name;
    private String slug;
    private List<ProductPinnedDto> products;
}
