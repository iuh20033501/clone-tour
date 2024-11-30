package iuh.edu.fit.services;

import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;

import java.util.List;

public interface OrderService {
    List<User> getCustomersByTour(Long tourId);

    Order createOrder(Long customerId, Long tourId, int soLuongKhach);

    Order getOrderById(Long orderId);

    List<Order> getOrdersByCustomerId(Long customerId);

    List<Order> getOrdersByCustomer(Long customerId);

    // Lấy tất cả các hóa đơn khách vãng lai
    List<Order> getAllHoaDon();
}
