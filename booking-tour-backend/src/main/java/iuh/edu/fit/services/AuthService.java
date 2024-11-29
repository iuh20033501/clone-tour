package iuh.edu.fit.services;

import iuh.edu.fit.dto.LoginDTO;
import iuh.edu.fit.dto.RegisterDTO;
import iuh.edu.fit.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
   public User register(RegisterDTO registerDTO);
    public User login(LoginDTO loginDTO);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
