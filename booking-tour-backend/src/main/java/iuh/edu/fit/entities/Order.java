package iuh.edu.fit.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Mã hóa đơn (Primary Key)

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer; // Khách hàng đặt đơn (quan hệ Many-to-One với bảng User)

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour; // Tour được đặt (quan hệ Many-to-One với bảng Tour)

    @Column(nullable = false)
    private LocalDateTime createdDate; // Ngày lập hóa đơn

    @Column(nullable = false)
    private double totalAmount; // Thành tiền

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
