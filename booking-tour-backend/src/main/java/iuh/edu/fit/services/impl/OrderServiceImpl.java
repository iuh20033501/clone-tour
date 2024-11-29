package iuh.edu.fit.services.impl;

import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.Tour;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.OrderRepository;
import iuh.edu.fit.repository.TourRepository;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<User> getCustomersByTour(Long tourId) {
        List<Order> orders = orderRepository.findByTour_TourId(tourId);
        return orders.stream()
                .map(Order::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }
    @Override
    public Order createOrder(Long customerId, Long tourId, int soLuongKhach) {
        // Lấy thông tin khách hàng từ database
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Lấy thông tin tour từ database
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setCustomer(customer);
        order.setTour(tour);
        order.setSoLuongKhach(soLuongKhach);

        // Tính tổng tiền: giá tour * số lượng khách
        order.setTotalAmount(tour.getPrice() * soLuongKhach);
        // Cập nhật lại số lượng chỗ trống của tour
        tour.setAvailableSlots(tour.getAvailableSlots() - soLuongKhach);
        tourRepository.save(tour);
        // Lưu đơn hàng vào database
        return orderRepository.save(order);
    }
    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

}
