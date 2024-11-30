package iuh.edu.fit.services.impl;

import iuh.edu.fit.entities.Tour;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> getUsersByPhone(String phone) {
        return userRepository.findByPhoneContaining(phone);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findByRole("EMPLOYEE");
    }
    // Phương thức tìm khách hàng theo ID
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    // Phương thức tìm khách hàng theo email
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    // Phương thức sửa thông tin khách hàng
    @Override
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            System.out.println("User không tồn tại với ID: " + id);
            return null;
        }

        User existingUser = existingUserOpt.get();

        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }

        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            System.out.println("Mật khẩu trước khi mã hóa: " + updatedUser.getPassword());
            String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
            System.out.println("Mật khẩu sau khi mã hóa: " + encodedPassword);
            existingUser.setPassword(encodedPassword);
        }

        User savedUser = userRepository.save(existingUser);
        System.out.println("Mật khẩu trong DB sau khi lưu: " + savedUser.getPassword());
        return savedUser;
    }

    @Override
    public User changePass(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            System.out.println("User không tồn tại với ID: " + id);
            return null;
        }

        User existingUser = existingUserOpt.get();

        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }

        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }

        User savedUser = userRepository.save(existingUser);
        System.out.println("Mật khẩu trong DB sau khi lưu: " + savedUser.getPassword());
        return savedUser;
    }

    public List<User> getAllEmployees() {
        return userRepository.findByRole("EMPLOYEE");
    }

    public void deleteUserByID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


}
