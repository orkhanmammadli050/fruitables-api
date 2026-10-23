package az.edu.itbrains.fruitables.controllers;

import az.edu.itbrains.fruitables.dtos.basket.BasketUserDto;
import az.edu.itbrains.fruitables.dtos.order.OrderCreateDto;
import az.edu.itbrains.fruitables.repositories.OrderRepository;
import az.edu.itbrains.fruitables.services.BasketService;
import az.edu.itbrains.fruitables.services.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;
    private final OrderRepository orderRepository;

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> checkout(Principal principal, HttpSession session) {
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

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> payment(@Valid @RequestBody OrderCreateDto orderCreateDto,
                                                       Principal principal,
                                                       HttpSession session) {
        String email = principal.getName();
        String orderNumber = orderService.createOrder(orderCreateDto, email);

        String couponCode = (String) session.getAttribute("couponCode");
        if (couponCode != null) {
            basketService.markCouponAsUsed(couponCode, email);
            session.removeAttribute("discountPercent");
            session.removeAttribute("couponCode");
        }

        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("orderNumber", orderNumber);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/dashboard/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> orders() {
        Map<String, Object> body = new HashMap<>();
        body.put("orders", orderRepository.findAll());
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/dashboard/order/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}