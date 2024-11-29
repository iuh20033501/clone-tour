package iuh.edu.fit.dto;

import iuh.edu.fit.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String avatar;
    private String role;


    public UserDetailDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.role = user.getRole();
    }
}
