package iuh.edu.fit.services;

import iuh.edu.fit.entities.User;

import java.util.List;

public interface OrderService {
    List<User> getCustomersByTour(Long tourId);
}
