package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.basket.BasketAddDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUpdateDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUserDto;

import java.math.BigDecimal;
import java.util.List;

public interface BasketService {
    List<BasketUserDto> getBasketItems(String email);

    void createBasketItem(BasketAddDto basketAddDto, String email);

    void deleteByProductId(Long productId, String email);
    void updateQuantity(BasketUpdateDto dto, String email);

    BigDecimal applyCoupon(String code, String email);

    void markCouponAsUsed(String couponCode, String email);
}
