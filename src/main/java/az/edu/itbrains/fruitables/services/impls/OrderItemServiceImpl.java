package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.basket.BasketUserDto;
import az.edu.itbrains.fruitables.models.Order;
import az.edu.itbrains.fruitables.models.OrderItem;
import az.edu.itbrains.fruitables.models.Product;
import az.edu.itbrains.fruitables.models.User;
import az.edu.itbrains.fruitables.repositories.BasketRepository;
import az.edu.itbrains.fruitables.repositories.OrderItemRepository;
import az.edu.itbrains.fruitables.repositories.ProductRepository;
import az.edu.itbrains.fruitables.services.BasketService;
import az.edu.itbrains.fruitables.services.OrderItemService;
import az.edu.itbrains.fruitables.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final BasketService basketService;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrderItems(Order order, String email) {

        List<BasketUserDto> basketUserDtoList = basketService.getBasketItems(email);

        for (BasketUserDto basketUserDto : basketUserDtoList) {
            OrderItem orderItem = new OrderItem();
            Product product = productService.getProductById(basketUserDto.getProduct().getId());
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(basketUserDto.getQuantity());
            orderItem.setPrice(basketUserDto.getProduct().getPrice());
            orderItemRepository.save(orderItem);

            product.setSalesCount(product.getSalesCount() + basketUserDto.getQuantity());
            productRepository.save(product);
        }

        basketRepository.deleteByUserEmail(email);

    }
}
