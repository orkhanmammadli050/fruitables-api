package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.dtos.order.OrderCreateDto;

public interface OrderService {
    String createOrder(OrderCreateDto orderCreateDto, String email);

    void deleteOrder(Long id);
}
