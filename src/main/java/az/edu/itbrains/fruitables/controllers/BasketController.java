package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.basket.BasketAddDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUpdateDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUserDto;
import az.edu.itbrains.fruitables.dtos.basket.CouponApplyDto;
import az.edu.itbrains.fruitables.services.BasketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> basket(Principal principal, HttpSession session) {
        String email = principal.getName();
        List<BasketUserDto> basketItemUserDtoList = basketService.getBasketItems(email);

        BigDecimal subtotal = BigDecimal.ZERO;
        for (BasketUserDto item : basketItemUserDtoList) {
            if (item.getTotalPrice() != null) {
                subtotal = subtotal.add(item.getTotalPrice());
            }
        }

        BigDecimal discountPercent = (BigDecimal) session.getAttribute("discountPercent");
        BigDecimal discountedAmount = BigDecimal.ZERO;
        BigDecimal total = subtotal;

        if (discountPercent != null) {
            discountedAmount = subtotal.multiply(discountPercent)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            total = subtotal.subtract(discountedAmount);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("baskets", basketItemUserDtoList);
        body.put("subtotal", subtotal);
        body.put("total", total);
        body.put("discountedAmount", discountedAmount);

        return ResponseEntity.ok(body);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addToCart(@Valid @RequestBody BasketAddDto basketAddDto, Principal principal) {
        String email = principal.getName();
        basketService.createBasketItem(basketAddDto, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/quantity")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> updateQuantity(@Valid @RequestBody BasketUpdateDto dto, Principal principal) {
        String email = principal.getName();
        basketService.updateQuantity(dto, email);
        return ResponseEntity.ok(Map.of("success", true, "message", "Say yeniləndi"));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteBasketItem(@PathVariable Long productId, Principal principal) {
        String email = principal.getName();
        basketService.deleteByProductId(productId, email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/apply-coupon")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> applyCoupon(@Valid @RequestBody CouponApplyDto couponApplyDto,
                                                           Principal principal,
                                                           HttpSession session) {
        String email = principal.getName();
        BigDecimal discountPercent = basketService.applyCoupon(couponApplyDto.getCode(), email);

        session.setAttribute("discountPercent", discountPercent);
        session.setAttribute("couponCode", couponApplyDto.getCode());

        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("discountPercent", discountPercent);
        return ResponseEntity.ok(body);
    }
}