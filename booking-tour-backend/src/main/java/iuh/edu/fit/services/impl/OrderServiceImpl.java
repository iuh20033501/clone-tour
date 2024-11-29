package iuh.edu.fit.services.impl;

import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.OrderRepository;
import iuh.edu.fit.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<User> getCustomersByTour(Long tourId) {
        List<Order> orders = orderRepository.findByTour_TourId(tourId);
        return orders.stream()
                .map(Order::getCustomer)
                .distinct()
                .collect(Collectors.toList());
    }
}
