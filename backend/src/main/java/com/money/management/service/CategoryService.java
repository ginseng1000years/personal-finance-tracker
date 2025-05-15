package com.money.management.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.money.management.entity.Category;

public interface CategoryService extends CrudService<Category, String> {
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
}