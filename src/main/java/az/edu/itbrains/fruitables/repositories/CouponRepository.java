package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByCodeAndIsActiveTrue(String code);

    Optional<Coupon> findByCode(String code);
}
