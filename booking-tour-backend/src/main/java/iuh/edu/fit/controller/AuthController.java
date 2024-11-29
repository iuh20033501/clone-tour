package iuh.edu.fit.controller;

import iuh.edu.fit.dto.*;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.repository.OTPRepository;
import iuh.edu.fit.services.AuthService;
import iuh.edu.fit.services.impl.JwtServiceImpl;
import iuh.edu.fit.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println("Name: " + registerDTO.getFullName());
        System.out.println("Phone: " + registerDTO.getPhone());
        System.out.println("Email: " + registerDTO.getEmail());
        System.out.println("Password: " + registerDTO.getPassword());
        System.out.println("Avatar: " + registerDTO.getAvatar());
        System.out.println("Role: " + registerDTO.getRole());

        // Xử lý đăng ký
        User newUser = authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng kí thành công");
    }

    @PostMapping("/registerEmployee")
    public ResponseEntity<String> registerEmployee(@ModelAttribute RegisterDTO registerDTO) {
        //    ModelAttribute sử dụng cho form data
        System.out.println("Name: " + registerDTO);
        System.out.println("Name: " + registerDTO.getFullName());
        System.out.println("Phone: " + registerDTO.getPhone());
        System.out.println("Email: " + registerDTO.getEmail());
        System.out.println("Password: " + registerDTO.getPassword());
        // Xử lý đăng ký
        User newUser = authService.register(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtServiceImpl.generateToken(userDetails);
            String refreshToken = jwtServiceImpl.generateRefreshToken(null, userDetails);

            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("UNKNOWN");

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, role));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Đăng nhập thát bại");
        }
    }
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOTP(@RequestBody OtpRequest otpRequest) {
        String phone = otpRequest.getPhone();
        if (!userServiceImpl.getUsersByPhone(phone).isEmpty()) {
            return ResponseEntity.badRequest().body("Số điện thoại đã được sử dụng");
        }
        String otp = otpRepository.generateOTP(phone);
        log.info("OTP cho số điện thoại {} là: {}", phone, otp);
        return ResponseEntity.ok(otp);
    }
    @PostMapping("/confirm-otp")
    public ResponseEntity<?> confirmOTP(@RequestBody OtpVerificationRequest otpRequest) {
        String phone = otpRequest.getPhone();
        String otp = otpRequest.getOtp();
        if (!otpRepository.validateOTP(phone, otp)) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc đã hết hạn.");
        }
        return ResponseEntity.ok("Xác thực OTP thành công!");
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> optionalUser = userServiceImpl.getUserByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(404).body("Tài khoản không tồn tại");
            }
            User user = optionalUser.get();
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body("Mật khẩu cũ không chính xác");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userServiceImpl.updateUser(user.getId(),user);
            return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Có lỗi xảy ra");
        }
    }

    @PostMapping("/forgot-password/request-otp")
    public ResponseEntity<?> requestForgotPasswordOTP(@RequestBody OtpRequest otpRequest) {
        String phone = otpRequest.getPhone();
        Optional<User> optionalUser = userServiceImpl.getUsersByPhone(phone);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Số điện thoại không tồn tại trong hệ thống.");
        }
        String otp = otpRepository.generateOTP(phone);
        log.info("OTP cho số điện thoại {} là: {}", phone, otp);
        return ResponseEntity.ok(otp);
    }


    @PostMapping("/forgot-password/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        String phone = request.getPhone();
        String newPassword = request.getNewPassword();
        Optional<User> optionalUser = userServiceImpl.getUsersByPhone(phone);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("Tài khoản không tồn tại.");
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userServiceImpl.updateUser(user.getId(),user);

        return ResponseEntity.ok("Đặt lại mật khẩu thành công.");
    }
}