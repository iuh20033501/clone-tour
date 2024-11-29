package iuh.edu.fit.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHoTenKhachHang() {
        return hoTenKhachHang;
    }

    public void setHoTenKhachHang(String hoTenKhachHang) {
        this.hoTenKhachHang = hoTenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public int getSoLuongKhach() {
        return soLuongKhach;
    }

    public void setSoLuongKhach(int soLuongKhach) {
        this.soLuongKhach = soLuongKhach;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
