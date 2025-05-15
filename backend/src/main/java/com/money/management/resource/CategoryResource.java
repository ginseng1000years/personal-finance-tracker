package com.money.management.resource;

import com.money.management.service.CategoryService;
import com.money.management.dto.CategoryDTO;
import com.money.management.entity.Category;
import com.money.management.mapper.CategoryMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    
    @Autowired
    public CategoryResource(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }
    
    @GetMapping
    public Page<CategoryDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return categoryService.findAll(PageRequest.of(page, size))
                .map(categoryMapper::toDto);
    }
    
    @PostMapping
    public CategoryDTO save(@RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getType() == null || (!categoryDTO.getType().equals("expense") && !categoryDTO.getType().equals("income"))) {
            throw new IllegalArgumentException("Category type must be either 'expense' or 'income'");
        }
        Category category = categoryService.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toDto(category);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getType() == null || (!categoryDTO.getType().equals("expense") && !categoryDTO.getType().equals("income"))) {
            throw new IllegalArgumentException("Category type must be either 'expense' or 'income'");
        }

        categoryDTO.setId(id);
        Category category = categoryService.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toDto(category);
    }
    
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        categoryService.deleteById(id);
    }
}