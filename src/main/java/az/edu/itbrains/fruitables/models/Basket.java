package az.edu.itbrains.fruitables.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int quantity;

    @ManyToOne
    User user;

    @ManyToOne
    Product product;
}
