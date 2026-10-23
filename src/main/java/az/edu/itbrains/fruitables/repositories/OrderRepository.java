package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
