package iuh.edu.fit.controller;
import iuh.edu.fit.entities.HoaDonKhachVangLai;
import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.OrderRepository;
import iuh.edu.fit.repository.TourRepository;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.OrderService;
import iuh.edu.fit.services.impl.OrderServiceImpl;
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
    private final OrderServiceImpl orderServiceImpl;

    public OrderController(OrderRepository orderRepository, UserRepository userRepository, TourRepository tourRepository, OrderService orderService, OrderServiceImpl orderServiceImpl) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.orderService = orderService;
        this.orderServiceImpl = orderServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam Long customerId,
                                         @RequestParam Long tourId,
                                         @RequestParam int soLuongKhach) {
        try {
            // Tạo đơn hàng mới
            Order order = orderServiceImpl.createOrder(customerId, tourId, soLuongKhach);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // API lấy danh sách các tour đã đặt của khách hàng
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderServiceImpl.getOrdersByCustomer(customerId);

        if (orders.isEmpty()) {
            return ResponseEntity.ok("No orders found for this customer.");
        }

        return ResponseEntity.ok(orders);
    }
    // API lấy danh sách khách hàng đã đặt tour
    @GetMapping("/customers/{tourId}")
    public List<User> getCustomersByTour(@PathVariable Long tourId) {
        return orderServiceImpl.getCustomersByTour(tourId); // Trả về danh sách khách hàng đã đặt tour
    }
    // API xem chi tiết hóa đơn
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            // Lấy thông tin hóa đơn từ service
            Order order = orderServiceImpl.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllHoaDon() {
        List<Order> orders = orderService.getAllHoaDon();
        return ResponseEntity.ok(orders);
    }

}