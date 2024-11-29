package iuh.edu.fit.controller;

import iuh.edu.fit.entities.User;
import iuh.edu.fit.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private AuthService authService;

    @GetMapping("/getuser")
    public ResponseEntity<User> getUser() {
        // Create fake user data
        User fakeUser = new User();
        fakeUser.setId(1L);
        fakeUser.setEmail("john.doe@example.com");

        // Return the fake user data with HTTP status 200 OK
        return new ResponseEntity<>(fakeUser, HttpStatus.OK);
    }


}
