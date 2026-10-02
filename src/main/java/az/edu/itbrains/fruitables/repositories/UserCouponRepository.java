package az.edu.itbrains.fruitables.repositories;

import az.edu.itbrains.fruitables.models.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);
}
