package iuh.edu.fit.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String phone;
    private String newPassword;
}