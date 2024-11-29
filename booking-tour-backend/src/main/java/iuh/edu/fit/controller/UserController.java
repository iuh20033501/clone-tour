package iuh.edu.fit.controller;

import iuh.edu.fit.dto.UserDetailDTO;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.AuthService;
import iuh.edu.fit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có người dùng
        }

        // Trả về danh sách người dùng với mã 200 OK
        return ResponseEntity.ok(users);
    }
    @GetMapping("/search")
    public ResponseEntity<User> getUsersByPhone(
            @RequestParam String phone // Nhận tham số phone từ query
    ) {
        Optional<User> users = userServiceImpl.getUsersByPhone(phone);
        if (users.isPresent()) {
            return ResponseEntity.ok(users.get()); // Trả về khách hàng với mã 200 OK nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy khách hàng
        }
    }
    // Phương thức tìm khách hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userServiceImpl.getUserById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Trả về khách hàng với mã 200 OK nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy khách hàng
        }
    }
    // Phương thức tìm khách hàng theo email
    @GetMapping("/searchByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userServiceImpl.getUserByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // Trả về khách hàng với mã 200 OK nếu tìm thấy
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy khách hàng
        }
    }
    // API sửa thông tin khách hàng
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Gọi phương thức updateUser từ service để cập nhật thông tin khách hàng
        User updatedUserResult = userServiceImpl.updateUser(id, updatedUser);

        // Kiểm tra nếu không tìm thấy người dùng
        if (updatedUserResult == null) {
            // Trả về mã 404 Not Found nếu không tìm thấy người dùng
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Trả về thông tin khách hàng sau khi cập nhật với mã 200 OK
        return ResponseEntity.ok(updatedUserResult);
    }
    @GetMapping("/getMyProfile")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        String email = authentication.getName(); // Lấy email từ Authentication
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetailDTO userDTO = new UserDetailDTO(user);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/employees")
    public ResponseEntity<List<User>> getAllEmployees() {
        List<User> employees = userServiceImpl.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userServiceImpl.deleteUserByID(id);
            return ResponseEntity.ok("User with id " + id + " has been deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
