package iuh.edu.fit.repository;

import iuh.edu.fit.entities.Order;
import iuh.edu.fit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTour_TourId(Long tourId);
    List<Order> findByCustomer(Optional<User> user);
}
