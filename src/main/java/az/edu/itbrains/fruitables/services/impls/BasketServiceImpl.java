package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.basket.BasketAddDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUpdateDto;
import az.edu.itbrains.fruitables.dtos.basket.BasketUserDto;
import az.edu.itbrains.fruitables.models.*;
import az.edu.itbrains.fruitables.repositories.BasketRepository;
import az.edu.itbrains.fruitables.repositories.CouponRepository;
import az.edu.itbrains.fruitables.repositories.UserCouponRepository;
import az.edu.itbrains.fruitables.services.BasketService;
import az.edu.itbrains.fruitables.services.ProductService;
import az.edu.itbrains.fruitables.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;


    @Override
    public BigDecimal applyCoupon(String code, String email) {

        User user = userService.getUserByEmail(email);

        Coupon coupon = couponRepository.findByCodeAndIsActiveTrue(code)
                .orElseThrow(() -> new RuntimeException("Yanlış və ya aktiv olmayan kupon kodu!"));


        if (coupon.getExpiryDate() != null && coupon.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Bu kuponun son istifadə tarixi bitib!");
        }


        boolean alreadyUsed = userCouponRepository.existsByUserIdAndCouponId(user.getId(), coupon.getId());
        if (alreadyUsed) {
            throw new RuntimeException("Siz bu kuponu artıq daha əvvəl istifadə etmisiniz!");
        }

        List<BasketUserDto> basketItems = getBasketItems(email);
        BigDecimal subtotal = basketItems.stream()
                .map(BasketUserDto::getTotalPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (coupon.getMinSpend() != null && subtotal.compareTo(coupon.getMinSpend()) < 0) {
            throw new RuntimeException("Kuponun tətbiqi üçün səbətin ümumi məbləği ən azı " + coupon.getMinSpend() + " ₼ olmalıdır!");
        }

        return BigDecimal.valueOf(coupon.getDiscountPercent());
    }


    public void markCouponAsUsed(String code, String email) {
        User user = userService.getUserByEmail(email);
        Coupon coupon = couponRepository.findByCode(code).orElse(null);

        if (coupon != null && user != null) {
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUser(user);
            userCoupon.setCoupon(coupon);
            userCouponRepository.save(userCoupon);
        }
    }


    @Override
    @Transactional
    public void updateQuantity(BasketUpdateDto dto, String email) {

        Basket basket = basketRepository.findByProductIdAndUserEmail(dto.getProductId(), email)
                .orElseThrow(() -> new RuntimeException("Səbət tapılmadı"));

        basket.setQuantity(dto.getQuantity());
        basketRepository.save(basket);
    }


    @Override
    @Transactional
    public void deleteByProductId(Long productId, String email) {

        basketRepository.deleteByProductIdAndUserEmail(productId, email);
    }


    @Override
    public List<BasketUserDto> getBasketItems(String email) {
        User user = userService.getUserByEmail(email);
        List<Basket> basketList = user.getBaskets();

        if (!basketList.isEmpty()) {
            return basketList.stream().map(basket -> {
                BasketUserDto dto = modelMapper.map(basket, BasketUserDto.class);

                if (basket.getProduct() != null && basket.getProduct().getPhotos() != null
                        && !basket.getProduct().getPhotos().isEmpty()) {
                    String photoUrl = basket.getProduct().getPhotos().get(0).getUrl();
                    dto.getProduct().setPhotoUrl(photoUrl);
                    System.out.println("Şəkil tapıldı: " + photoUrl);
                } else {
                    System.out.println("Şəkil TAPILMADI və ya boşdur: " + basket.getProduct().getName());
                }

                return dto;
            }).toList();
        }
        return List.of();
    }


    @Override
    public void createBasketItem(BasketAddDto basketAddDto, String email) {
        User user = userService.getUserByEmail(email);
        Product product = productService.getProductById(basketAddDto.getProductId());

        Basket findProductBasket = basketRepository.findByProductIdAndUserId(basketAddDto.getProductId(), user.getId());

        if(findProductBasket != null){
            findProductBasket.setQuantity(findProductBasket.getQuantity() + basketAddDto.getQuantity());
            basketRepository.save(findProductBasket);
            return;
        }

        Basket basket = new Basket();
        basket.setQuantity(basketAddDto.getQuantity());
        basket.setProduct(product);
        basket.setUser(user);
        basketRepository.save(basket);

    }
}
