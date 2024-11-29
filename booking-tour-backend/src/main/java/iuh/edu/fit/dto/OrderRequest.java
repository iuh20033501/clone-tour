package iuh.edu.fit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long customerId; // ID khách hàng
    private Long tourId;     // ID tour
    private double totalAmount; // Thành tiền

}
