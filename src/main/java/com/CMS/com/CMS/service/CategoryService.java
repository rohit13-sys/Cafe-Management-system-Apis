package com.CMS.com.CMS.service;

import com.CMS.com.CMS.pojo.ItemCategory;
import com.CMS.com.CMS.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends GenericService<ItemCategory>{

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository repository) {
        super(repository);
        this.categoryRepository=repository;
    }
}
