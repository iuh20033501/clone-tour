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
        return userRepository.findByPhoneContaining(phone); // Tìm kiếm theo số điện thoại
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Trả về tất cả người dùng
    }
    // Phương thức tìm khách hàng theo ID
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id); // Trả về một Optional<User> nếu tìm thấy khách hàng
    }
    // Phương thức tìm khách hàng theo email
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Giả sử UserRepository có phương thức findByEmail()
    }
    // Phương thức sửa thông tin khách hàng
    @Override
    public User updateUser(Long id, User updatedUser) {
        // Kiểm tra xem khách hàng có tồn tại hay không
        Optional<User> existingUserOpt = userRepository.findById(id);

        // Nếu không tìm thấy khách hàng, trả về null hoặc có thể ném ra một Exception
        if (!existingUserOpt.isPresent()) {
            return null; // Hoặc có thể ném ra exception nếu muốn
        }

        User existingUser = existingUserOpt.get();

        // Cập nhật các trường thông tin của khách hàng
        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getPhone() != null) {
            existingUser.setPhone(updatedUser.getPhone());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        // Tiếp tục cập nhật các trường khác nếu có

        // Lưu thông tin khách hàng đã được cập nhật vào cơ sở dữ liệu
        return userRepository.save(existingUser);
    }

    public List<User> getAllEmployees() {
        // Lọc danh sách user theo role EMPLOYEE
        return userRepository.findByRole("EMPLOYEE");
    }

    public void deleteUserByID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }


}
