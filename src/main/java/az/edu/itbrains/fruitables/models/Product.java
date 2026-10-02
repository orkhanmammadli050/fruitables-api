package az.edu.itbrains.fruitables.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private double cashbackPercent;
    private int quantity;
    private boolean isStock;
    private boolean isNew;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isBestSeller;

    @Column( nullable = false, columnDefinition = "boolean default false")
    private boolean isFeatured;

    @Column(unique = true)
    private String slug;

    @Column(name = "price",precision = 10,scale = 2)
    private BigDecimal price;

    @Column(name = "discount",precision = 10,scale = 2)
    private BigDecimal discount;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Photo> photos = new ArrayList<>();


    @Column(name = "sales_count", nullable = false)
    private int salesCount = 0;
}
