package iuh.edu.fit.services.impl;

import iuh.edu.fit.dto.TourDTO;
import iuh.edu.fit.entities.Category;
import iuh.edu.fit.entities.Tour;
import iuh.edu.fit.repository.CategoryRepository;
import iuh.edu.fit.repository.TourRepository;
import iuh.edu.fit.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private TourDTO convertToDTO(Tour tour) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setTourId(tour.getTourId());
        tourDTO.setName(tour.getName());
        tourDTO.setDescription(tour.getDescription());
        tourDTO.setDestination(tour.getDestination());
        tourDTO.setPrice(tour.getPrice());
        tourDTO.setDuration(tour.getDuration());
        tourDTO.setStartDate(tour.getStartDate());
        tourDTO.setEndDate(tour.getEndDate());
        tourDTO.setCapacity(tour.getCapacity());
        tourDTO.setAvailableSlots(tour.getAvailableSlots());
        tourDTO.setImages(tour.getImages());
        tourDTO.setRating(tour.getRating());
        // Thay categoryId bằng categoryName
        tourDTO.setCategoryName(tour.getCategory() != null ? tour.getCategory().getName() : null);
        tourDTO.setCreatedAt(tour.getCreatedAt());
        tourDTO.setUpdatedAt(tour.getUpdatedAt());
        return tourDTO;
    }

    @Override
    public TourDTO getTourById(Long id) {
        return tourRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("không tìm thấy tour có id: " + id));
    }

    @Override
    public List<TourDTO> getAllTours() {
        List<Tour> tours = tourRepository.findAll();
        List<TourDTO> tourDTOList = new ArrayList<>();  // Tạo một danh sách mới để chứa các TourDTO

        for (Tour tour : tours) {
            // Chuyển từng đối tượng Tour thành TourDTO và thêm vào danh sách tourDTOList
            tourDTOList.add(convertToDTO(tour));
        }

        return tourDTOList;  // Trả về danh sách các TourDTO
    }


    @Override
    public TourDTO addTour(TourDTO tourDTO) {
        // Tìm category theo tên
        Category category = categoryRepository.findByName(tourDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Tạo đối tượng tour từ DTO và gán category
        Tour tour = new Tour();
        tour.setName(tourDTO.getName());
        tour.setDescription(tourDTO.getDescription());
        tour.setDestination(tourDTO.getDestination());
        tour.setPrice(tourDTO.getPrice());
        tour.setDuration(tourDTO.getDuration());
        tour.setStartDate(tourDTO.getStartDate());
        tour.setEndDate(tourDTO.getEndDate());
        tour.setCapacity(tourDTO.getCapacity());
        tour.setAvailableSlots(tourDTO.getAvailableSlots());
        tour.setImages(tourDTO.getImages());
        tour.setRating(tourDTO.getRating());
        tour.setCategory(category);

        // Lưu tour vào DB
        tourRepository.save(tour);

        // Trả về TourDTO
        return tourDTO;
    }


    @Override
    public List<TourDTO> getToursByCategory(String categoryName) {
        // Tìm category theo tên
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Tìm tất cả tour có category tương ứng
        List<Tour> tours = tourRepository.findByCategory(category);

        // Chuyển tất cả tour thành TourDTO và trả về
        return tours.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteTourById(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(()-> new RuntimeException("Tour not found"+tourId));
        tourRepository.delete(tour);
    }
    // Cập nhật thông tin tour
    @Override
    public Tour updateTour(Long id, Tour updatedTour) {
        // Lấy tour từ DB
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour với ID: " + id));

        // Cập nhật các trường
        existingTour.setName(updatedTour.getName());
        existingTour.setPrice(updatedTour.getPrice());
        existingTour.setDescription(updatedTour.getDescription());
        existingTour.setDestination(updatedTour.getDestination());
        existingTour.setDuration(updatedTour.getDuration());
        existingTour.setCapacity(updatedTour.getCapacity());
        existingTour.setAvailableSlots(updatedTour.getAvailableSlots());
        existingTour.setImages(updatedTour.getImages());
        existingTour.setEndDate(updatedTour.getEndDate());
        existingTour.setStartDate(updatedTour.getStartDate());

        // Lưu lại
        return tourRepository.save(existingTour);
    }
}
