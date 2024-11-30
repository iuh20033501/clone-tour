package iuh.edu.fit.repository;

import iuh.edu.fit.entities.HoaDonKhachVangLai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonKhachVangLaiRepository extends JpaRepository<HoaDonKhachVangLai, Integer> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
    List<HoaDonKhachVangLai> findByTour_TourId(Long tourId);
    List<HoaDonKhachVangLai> findByEmail(String email);
    List<HoaDonKhachVangLai> findBySoDienThoai(String soDienThoai);

}