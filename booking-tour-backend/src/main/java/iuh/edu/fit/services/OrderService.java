package iuh.edu.fit.services;

import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;

import java.util.List;

public interface OrderService {
    List<User> getCustomersByTour(Long tourId);

    Order createOrder(Long customerId, Long tourId, int soLuongKhach);

    Order getOrderById(Long orderId);
}
