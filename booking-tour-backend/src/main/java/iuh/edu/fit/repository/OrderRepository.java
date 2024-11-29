package iuh.edu.fit.repository;

import iuh.edu.fit.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByTour_TourId(Long tourId);
}
