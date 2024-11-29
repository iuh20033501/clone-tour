package iuh.edu.fit.services.impl;

import iuh.edu.fit.entities.Category;
import iuh.edu.fit.repository.CategoryRepository;
import iuh.edu.fit.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }
}
