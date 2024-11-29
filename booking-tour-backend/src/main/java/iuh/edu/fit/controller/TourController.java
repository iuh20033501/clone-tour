package iuh.edu.fit.controller;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.edu.fit.dto.TourDTO;
import iuh.edu.fit.repository.CategoryRepository;
import iuh.edu.fit.services.CloudinaryService;
import iuh.edu.fit.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tours")
@CrossOrigin(origins = "http://localhost:5173")
public class TourController {

    @Autowired
    private TourService tourService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/all")
    public ResponseEntity<List<TourDTO>> getAllTours() {
        // Lấy danh sách tour từ dịch vụ
        List<TourDTO> tours = tourService.getAllTours();

        // Trả về danh sách tour với mã trạng thái HTTP 200 (OK)
        return ResponseEntity.ok(tours);
    }
    @GetMapping("/{tourId}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Long tourId) {
        TourDTO tour = tourService.getTourById(tourId);
        return ResponseEntity.ok(tour);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTour(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("destination") String destination,
            @RequestParam("price") Double price,
            @RequestParam("duration") String duration,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("availableSlots") Integer availableSlots,
            @RequestParam("rating") Double rating,
            @RequestParam("nameCategory") String nameCategory,
            @RequestParam("image") MultipartFile image) {


        // Xử lý ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start, end;
        try {
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use 'yyyy-MM-dd'.");
        }
        System.out.print("image:"+image);
        // Upload ảnh lên Cloudinary
        String imageUrl;
        try {
            imageUrl = cloudinaryService.uploadImage(image);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image to Cloudinary.");
        }

        // Tạo đối tượng TourDTO từ dữ liệu gửi đến
        TourDTO tourDTO = new TourDTO();
        tourDTO.setName(name);
        tourDTO.setDescription(description);
        tourDTO.setDestination(destination);
        tourDTO.setPrice(price);
        tourDTO.setDuration(duration);
        tourDTO.setStartDate(start);
        tourDTO.setEndDate(end);
        tourDTO.setCapacity(capacity);
        tourDTO.setAvailableSlots(availableSlots);
        tourDTO.setRating(rating);
        tourDTO.setCategoryName(nameCategory);
        tourDTO.setImages(imageUrl); // Lưu URL ảnh vào DTO

        // Lưu đối tượng TourDTO vào DB thông qua service
        tourService.addTour(tourDTO);

        return ResponseEntity.ok(tourDTO); // Trả về đối tượng TourDTO sau khi thêm thành công
    }


    @GetMapping("/category")
    public ResponseEntity<List<TourDTO>> getToursByCategory(@RequestParam String categoryName) {
        // Kiểm tra nếu tên danh mục không rỗng
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Trả về 400 Bad Request nếu categoryName không hợp lệ
        }

        // Gọi phương thức trong service để lấy danh sách tour theo danh mục
        List<TourDTO> tours = tourService.getToursByCategory(categoryName);

        // Kiểm tra nếu không tìm thấy tour nào với danh mục này
        if (tours.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Trả về 404 nếu không tìm thấy tour
        }

        // Trả về 200 OK và danh sách các tour
        return ResponseEntity.ok(tours);
    }
    @DeleteMapping("/delete/{tourId}")
    public ResponseEntity<?> deleteTour(@PathVariable Long tourId) {
        try {
            tourService.deleteTourById(tourId); // Gọi service xoá tour
            return ResponseEntity.ok("Tour with id " + tourId + " has been deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
