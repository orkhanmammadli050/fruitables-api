package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashbackRepository extends JpaRepository<Cashback,Long> {
}
