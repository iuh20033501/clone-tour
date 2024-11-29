package iuh.edu.fit.services.impl;

import iuh.edu.fit.dto.KhachHangVangLaiResponse;
import iuh.edu.fit.entities.HoaDonKhachVangLai;
import iuh.edu.fit.entities.Tour;
import iuh.edu.fit.repository.HoaDonKhachVangLaiRepository;
import iuh.edu.fit.repository.TourRepository;
import iuh.edu.fit.services.HoaDonKhachVangLaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoaDonKhachVangLaiServiceImpl implements HoaDonKhachVangLaiService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private HoaDonKhachVangLaiRepository hoaDonKhachVangLaiRepository;

    // Phương thức tạo hóa đơn
    @Override
    public HoaDonKhachVangLai createHoaDon(String hoTenKhachHang, String soDienThoai, String email, int soLuongKhach, Long tourId) {
        // Lấy thông tin tour từ tourId
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour không tồn tại"));

        // Kiểm tra số lượng khách có vượt quá số chỗ trống không
        if (tour.getAvailableSlots() < soLuongKhach) {
            throw new IllegalStateException("Không đủ chỗ trống cho số lượng khách yêu cầu.");
        }

        // Tính thành tiền
        double thanhTien = tour.getPrice() * soLuongKhach;

        // Tạo mới đối tượng hóa đơn
        HoaDonKhachVangLai hoaDon = new HoaDonKhachVangLai();
        hoaDon.setHoTenKhachHang(hoTenKhachHang);
        hoaDon.setSoDienThoai(soDienThoai);
        hoaDon.setEmail(email);
        hoaDon.setSoLuongKhach(soLuongKhach);
        hoaDon.setThanhTien(thanhTien);
        hoaDon.setTour(tour);  // Liên kết với tour
        hoaDon.setCreatedAt(LocalDateTime.now()); // Ghi nhận thời gian tạo hóa đơn

        // Lưu hóa đơn vào cơ sở dữ liệu
        HoaDonKhachVangLai savedHoaDon = hoaDonKhachVangLaiRepository.save(hoaDon);

        // Cập nhật lại số lượng chỗ trống của tour
        tour.setAvailableSlots(tour.getAvailableSlots() - soLuongKhach);
        tourRepository.save(tour);

        return savedHoaDon;
    }
    // Lấy danh sách khách hàng vãng lai theo id_tour
    @Override
    public List<KhachHangVangLaiResponse> getCustomersByTourId(Long tourId) {
        List<HoaDonKhachVangLai> hoaDons = hoaDonKhachVangLaiRepository.findByTour_TourId(tourId); // Truy vấn danh sách khách hàng theo tourId
        return hoaDons.stream()
                .map(hoaDon -> new KhachHangVangLaiResponse(hoaDon.getHoTenKhachHang(), hoaDon.getSoDienThoai(), hoaDon.getEmail(), hoaDon.getThanhTien()))
                .collect(Collectors.toList()); // Chuyển thành danh sách DTO
    }
    }
