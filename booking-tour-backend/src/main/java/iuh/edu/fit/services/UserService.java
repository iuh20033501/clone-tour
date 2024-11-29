package iuh.edu.fit.services;

import iuh.edu.fit.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUsersByPhone(String phone);

    List<User> getAllUsers();

    // Phương thức tìm khách hàng theo ID
    Optional<User> getUserById(Long id);

    // Phương thức tìm khách hàng theo email
    Optional<User> getUserByEmail(String email);

    // Phương thức sửa thông tin khách hàng
    User updateUser(Long id, User updatedUser);
}
