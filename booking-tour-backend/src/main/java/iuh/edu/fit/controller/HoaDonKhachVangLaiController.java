package iuh.edu.fit.controller;
import iuh.edu.fit.dto.KhachHangVangLaiResponse;
import iuh.edu.fit.entities.HoaDonKhachVangLai;
import iuh.edu.fit.services.impl.HoaDonKhachVangLaiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/hoadonkhachvanglai")
@CrossOrigin(origins = "http://localhost:5173")
public class HoaDonKhachVangLaiController {
    @Autowired
    private HoaDonKhachVangLaiServiceImpl hoaDonKhachVangLaiServiceImpl;

    public HoaDonKhachVangLaiController(HoaDonKhachVangLaiServiceImpl hoaDonKhachVangLaiServiceImpl) {
        this.hoaDonKhachVangLaiServiceImpl = hoaDonKhachVangLaiServiceImpl;
    }

    // API tạo hóa đơn cho khách vãng lai
    @PostMapping("/create")
    public ResponseEntity<?> createInvoiceForGuest(@RequestParam String hoTenKhachHang,
                                                   @RequestParam String soDienThoai,
                                                   @RequestParam String email,
                                                   @RequestParam int soLuongKhach,
                                                   @RequestParam Long tourId) {
        try {
            // Tạo hóa đơn và cập nhật lại số lượng availableSlots
            HoaDonKhachVangLai savedHoaDon = hoaDonKhachVangLaiServiceImpl.createHoaDon(
                    hoTenKhachHang,
                    soDienThoai,
                    email,
                    soLuongKhach,
                    tourId
            );
            return new ResponseEntity<>(savedHoaDon, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Trả về lỗi khi tour không đủ chỗ
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // API lấy danh sách khách hàng vãng lai theo tourId
    @GetMapping("/list/{tourId}")
    public ResponseEntity<List<KhachHangVangLaiResponse>> getCustomersByTourId(@PathVariable Long tourId) {
        List<KhachHangVangLaiResponse> customers = hoaDonKhachVangLaiServiceImpl.getCustomersByTourId(tourId);
        return ResponseEntity.ok(customers); // Trả về danh sách khách hàng
    }
    // API xem chi tiết hóa đơn khách vãng lai
    @GetMapping("/{id}")
    public ResponseEntity<?> getHoaDonById(@PathVariable Integer id) {
        try {
            HoaDonKhachVangLai hoaDon = hoaDonKhachVangLaiServiceImpl.getHoaDonKhachVangLaiById(id);
            return new ResponseEntity<>(hoaDon, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<HoaDonKhachVangLai>> getAllHoaDonKhachVangLai() {
        List<HoaDonKhachVangLai> hoaDons = hoaDonKhachVangLaiServiceImpl.getAllHoaDonKhachVangLai();
        return ResponseEntity.ok(hoaDons);
    }
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống.");
        }
        List<HoaDonKhachVangLai> results = hoaDonKhachVangLaiServiceImpl.findByEmail(email);
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy hóa đơn với email: " + email);
        }
        return ResponseEntity.ok(results);
    }
    @GetMapping("/findByPhone/{soDienThoai}")
    public ResponseEntity<?> findByPhone(@PathVariable String soDienThoai) {
        if (soDienThoai == null || soDienThoai.isEmpty()) {
            return ResponseEntity.badRequest().body("Số điện thoại không được để trống.");
        }
        List<HoaDonKhachVangLai> results = hoaDonKhachVangLaiServiceImpl.findBySoDienThoai(soDienThoai);
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy hóa đơn với số điện thoại: " + soDienThoai);
        }
        return ResponseEntity.ok(results);
    }

}
