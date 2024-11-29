package iuh.edu.fit.repository;

import iuh.edu.fit.entities.Category;
import iuh.edu.fit.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    // Add custom query here
    List<Tour> findByCategory(Category category);

}
