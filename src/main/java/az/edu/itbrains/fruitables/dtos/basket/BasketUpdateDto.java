package az.edu.itbrains.fruitables.dtos.basket;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketUpdateDto {
    private Long productId;
    private int quantity;
}
