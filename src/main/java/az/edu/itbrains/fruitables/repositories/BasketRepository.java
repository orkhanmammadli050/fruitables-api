package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket,Long> {

    Basket findByProductIdAndUserId(Long productId, Long id);

    void deleteByProductIdAndUserEmail(Long productId, String email);
    Optional<Basket> findByProductIdAndUserEmail(Long productId, String email);

    void deleteByUserEmail(String email);
}
