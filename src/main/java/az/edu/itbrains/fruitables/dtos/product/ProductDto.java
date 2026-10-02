package az.edu.itbrains.fruitables.dtos.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String slug;
    private BigDecimal price;
    private BigDecimal discount;
    private String photoUrl;

}
