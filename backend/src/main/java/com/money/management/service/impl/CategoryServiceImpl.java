package com.money.management.service.impl;

import com.money.management.entity.Category;
import com.money.management.repository.CategoryRepository;
import com.money.management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        if (category.getId() == null) {
            // This is a new category, check for duplicates
            if (categoryRepository.existsByName(category.getName())) {
                throw new IllegalStateException("Category already exists");
            }
        } else {
            // This is an update, find existing category
            Category existing = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            
            // Only check for duplicate name if name is being changed
            if (!existing.getName().equals(category.getName())) {
                if (categoryRepository.existsByName(category.getName())) {
                    throw new IllegalStateException("Category already exists");
                }
            }
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    public void deleteById(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found");
        }
        categoryRepository.deleteById(id);
    }


}