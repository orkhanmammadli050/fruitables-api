package az.edu.itbrains.fruitables.services.impls;

import az.edu.itbrains.fruitables.dtos.order.OrderCreateDto;
import az.edu.itbrains.fruitables.enums.OrderStatus;
import az.edu.itbrains.fruitables.models.Order;
import az.edu.itbrains.fruitables.models.User;
import az.edu.itbrains.fruitables.repositories.OrderRepository;
import az.edu.itbrains.fruitables.services.BasketService;
import az.edu.itbrains.fruitables.services.OrderItemService;
import az.edu.itbrains.fruitables.services.OrderService;
import az.edu.itbrains.fruitables.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;


    @Override
    @Transactional
    public String createOrder(OrderCreateDto orderCreateDto, String email) {

        User user = userService.getUserByEmail(email);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        order.setPhone(orderCreateDto.getPhone());
        order.setAddress(orderCreateDto.getAddress());

        orderRepository.save(order);

        orderItemService.createOrderItems(order, email);


        return "";
    }


    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
