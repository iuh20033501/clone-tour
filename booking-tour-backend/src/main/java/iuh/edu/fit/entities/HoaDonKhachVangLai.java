package iuh.edu.fit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoa_don_khach_vang_lai")
public class HoaDonKhachVangLai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ID tự động tăng

    @Column(name = "ho_ten_khach_hang", nullable = false)
    private String hoTenKhachHang; // Họ tên khách hàng

    @Column(name = "so_dien_thoai", nullable = false)
    private String soDienThoai; // Số điện thoại

    @Column(name = "email", nullable = false)
    private String email; // Email

    @Column(name = "thanh_tien", nullable = false)
    private double thanhTien; // Thành tiền, kiểu double

    @Column(name = "so_luong_khach", nullable = false)
    private int soLuongKhach; // Số lượng khách hàng

    @ManyToOne
    @JoinColumn(name = "id_tour", nullable = false) // Khóa ngoại tham chiếu đến bảng tour
    private Tour tour; // Liên kết đến Tour entity

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // Thời gian tạo hóa đơn

    // Getters và Setters


}
