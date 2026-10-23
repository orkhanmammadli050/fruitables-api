package az.edu.itbrains.fruitables.services;

import az.edu.itbrains.fruitables.models.Order;
import az.edu.itbrains.fruitables.models.User;

public interface OrderItemService {
    void createOrderItems(Order order, String email);
}
