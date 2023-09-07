package com.CMS.com.CMS.service;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T> {
    List<T> findAll();
    T save(T entity);
//    List<T> saveAll(List<T> entities);
    Optional<T> findById(String id);
    void delete(T entity);
    void deleteById(String id);
    void deleteAll(List<T> entities);
    long count();
}