package iuh.edu.fit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {
    private Long tourId;
    private String name;
    private String description;
    private String destination;
    private double price;
    private String duration;
    private Date startDate;
    private Date endDate;
    private int capacity;
    private int availableSlots;
    private String images;
    private double rating;
    private String categoryName;
    private Date createdAt;
    private Date updatedAt;


}
