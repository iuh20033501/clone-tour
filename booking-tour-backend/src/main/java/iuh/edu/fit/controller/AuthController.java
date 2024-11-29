package iuh.edu.fit.controller;

import iuh.edu.fit.dto.LoginDTO;
import iuh.edu.fit.dto.LoginResponse;
import iuh.edu.fit.dto.RegisterDTO;
import iuh.edu.fit.entities.User;
import iuh.edu.fit.services.AuthService;
import iuh.edu.fit.services.impl.JwtServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegisterDTO registerDTO) {
        //    ModelAttribute sử dụng cho form data
        System.out.println("Name: " + registerDTO);
        System.out.println("Name: " + registerDTO.getFullName());
        System.out.println("Phone: " + registerDTO.getPhone());
        System.out.println("Email: " + registerDTO.getEmail());
        System.out.println("Password: " + registerDTO.getPassword());
        System.out.println("Avatar: " + registerDTO.getAvatar());
        System.out.println("Role: " + registerDTO.getRole());

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
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}