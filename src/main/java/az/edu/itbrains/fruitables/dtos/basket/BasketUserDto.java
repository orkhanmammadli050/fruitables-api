package az.edu.itbrains.fruitables.dtos.basket;

import az.edu.itbrains.fruitables.dtos.product.ProductDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketUserDto {
    private Long id;
    private ProductDto product;
    private int quantity;

    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
       return this.totalPrice = product.getPrice().multiply(new BigDecimal(quantity));
    }
}
