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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        // Lấy thông tin User từ customerId
        Optional<User> user = userRepository.findById(customerId);
        if (user.isPresent()) {
            // Nếu User tồn tại, lấy danh sách Order liên quan
            return orderRepository.findByCustomer(user);
        } else {
            // Nếu không tìm thấy User, trả về danh sách rỗng
            System.out.println("User not found with ID: " + customerId);
            return new ArrayList<>();
        }
    }
    // Lấy tất cả các hóa đơn khách vãng lai
    @Override
    public List<Order> getAllHoaDon() {
        return orderRepository.findAll();
    }

}
