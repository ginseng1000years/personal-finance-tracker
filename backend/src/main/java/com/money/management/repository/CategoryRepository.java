package com.money.management.repository;

import com.money.management.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends MongoRepository<Category, String>, PagingAndSortingRepository<Category, String> {
    boolean existsByName(String name);
}