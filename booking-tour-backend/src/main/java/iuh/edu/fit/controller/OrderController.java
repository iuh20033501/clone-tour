package iuh.edu.fit.controller;
import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.OrderRepository;
import iuh.edu.fit.repository.TourRepository;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository, TourRepository tourRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam Long customerId,
                                         @RequestParam Long tourId,
                                         @RequestParam int soLuongKhach) {
        try {
            // Tạo đơn hàng mới
            Order order = orderService.createOrder(customerId, tourId, soLuongKhach);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // API lấy danh sách các tour đã đặt của khách hàng
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomer(@PathVariable Long customerId) {
        // Kiểm tra xem khách hàng có tồn tại trong hệ thống không
        Optional<User> customerOpt = userRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer not found!");
        }

        // Lấy danh sách các đơn hàng của khách hàng
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        if (orders.isEmpty()) {
            return ResponseEntity.ok("No orders found for this customer.");
        }

        // Trả về danh sách các tour mà khách hàng đã đặt
        return ResponseEntity.ok(orders);
    }
    // API lấy danh sách khách hàng đã đặt tour
    @GetMapping("/customers/{tourId}")
    public List<User> getCustomersByTour(@PathVariable Long tourId) {
        return orderService.getCustomersByTour(tourId); // Trả về danh sách khách hàng đã đặt tour
    }
}