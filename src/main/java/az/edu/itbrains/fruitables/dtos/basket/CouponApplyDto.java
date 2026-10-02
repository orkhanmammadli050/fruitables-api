package az.edu.itbrains.fruitables.dtos.basket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponApplyDto {
    @NotBlank(message = "Kupon kodu boş ola bilməz")
    private String code;
}
