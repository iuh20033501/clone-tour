package iuh.edu.fit.services.impl;

import iuh.edu.fit.dto.LoginDTO;
import iuh.edu.fit.dto.RegisterDTO;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.UserRepository;
import iuh.edu.fit.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements UserDetailsService, AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterDTO registerDTO) {
        // Kiểm tra xem email đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(registerDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        // Tạo người dùng mới
        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setAvatar(registerDTO.getAvatar());
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Override
    public User registerE(RegisterDTO registerDTO) {
        // Kiểm tra xem email đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(registerDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        // Tạo người dùng mới
        User user = new User();
        user.setFullName(registerDTO.getFullName());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setAvatar(registerDTO.getAvatar());
        user.setRole("EMPLOYEE");

        return userRepository.save(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        // Tìm người dùng theo email
        Optional<User> userOpt = userRepository.findByEmail(loginDTO.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email không tồn tại!");
        }

        User user = userOpt.get();

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu sai!");
        }

        return user; // Trả về người dùng nếu đăng nhập thành công
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        System.out.println("User found: " + user.get().getEmail());
        System.out.println("Role: " + user.get().getRole());
        System.out.println("Password: " + user.get().getPassword());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()))
                .build();
    }
}
