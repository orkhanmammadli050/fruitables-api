package az.edu.itbrains.fruitables.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDto {
    private Long id;
    private String slug;
    private String name;
    private String description;
    private BigDecimal price;
    private String photoUrl;

}
