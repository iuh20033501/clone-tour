package iuh.edu.fit.entities;

import jakarta.persistence.*;


import java.util.Date;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId; // Primary Key

    private String name; // Tên danh mục
    private String description; // Mô tả danh mục

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; // Ngày tạo

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // Ngày cập nhật

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
