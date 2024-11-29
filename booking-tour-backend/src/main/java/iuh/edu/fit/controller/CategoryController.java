package iuh.edu.fit.controller;

import iuh.edu.fit.entities.Category;
import iuh.edu.fit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        // Gọi service để lấy danh sách danh mục
        List<Category> categories = categoryService.getAllCategories();

        // Trả về danh sách danh mục với HTTP 200 (OK)
        return ResponseEntity.ok(categories);
    }
}
