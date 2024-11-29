package iuh.edu.fit.services;

import iuh.edu.fit.dto.TourDTO;
import iuh.edu.fit.entities.Tour;

import java.util.List;

public interface TourService {
    List<TourDTO>getAllTours();
    TourDTO getTourById(Long id);
    TourDTO addTour(TourDTO tourDTO);
    List<TourDTO> getToursByCategory(String categoryName);
    void deleteTourById(Long tourId);

    // Cập nhật thông tin tour
    Tour updateTour(Long id, Tour updatedTour);
}
