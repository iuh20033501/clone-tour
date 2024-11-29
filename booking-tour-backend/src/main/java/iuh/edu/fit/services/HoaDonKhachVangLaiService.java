package iuh.edu.fit.services;

import iuh.edu.fit.dto.KhachHangVangLaiResponse;
import iuh.edu.fit.entities.HoaDonKhachVangLai;

import java.util.List;

public interface HoaDonKhachVangLaiService {


    // Phương thức tạo hóa đơn
    HoaDonKhachVangLai createHoaDon(String hoTenKhachHang, String soDienThoai, String email, int soLuongKhach, Long tourId);

    // Lấy danh sách khách hàng vãng lai theo id_tour
    List<KhachHangVangLaiResponse> getCustomersByTourId(Long tourId);

    // Lấy hóa đơn khách vãng lai theo ID
    HoaDonKhachVangLai getHoaDonKhachVangLaiById(Integer id);
}
