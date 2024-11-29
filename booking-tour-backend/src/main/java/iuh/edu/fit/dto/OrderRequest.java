package iuh.edu.fit.dto;

public class OrderRequest {

    private Long customerId; // ID khách hàng
    private Long tourId;     // ID tour
    private double totalAmount; // Thành tiền

    // Getters và Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
