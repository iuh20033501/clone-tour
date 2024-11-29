package iuh.edu.fit.dto;

public class KhachHangVangLaiResponse {

    private String hoTen;
    private String soDienThoai;
    private String email;
    private double thanhTien;

    public KhachHangVangLaiResponse(String hoTen, String soDienThoai, String email, double thanhTien) {
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.thanhTien = thanhTien;
    }

    // Getters và Setters
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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
}
