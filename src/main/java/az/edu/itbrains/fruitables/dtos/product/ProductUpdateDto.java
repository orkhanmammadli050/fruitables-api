package az.edu.itbrains.fruitables.dtos.product;

import jakarta.persistence.SequenceGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;
    private String shortDescription;
    private int quantity;
    private double cashbackPercent;
    private Long categoryId;
}
