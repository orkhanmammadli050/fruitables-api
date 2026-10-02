package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
