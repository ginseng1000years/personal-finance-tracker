package com.money.management.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T save(T entity);
    T findById(ID id);
    void deleteById(ID id);
}