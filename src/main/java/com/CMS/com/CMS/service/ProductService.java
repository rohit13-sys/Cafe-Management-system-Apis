package com.CMS.com.CMS.service;

import com.CMS.com.CMS.pojo.Product;
import com.CMS.com.CMS.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends GenericService<Product>{


    @Autowired
    private ProductRepository productRepository;
    public ProductService(ProductRepository repository) {
        super(repository);
        this.productRepository=repository;
    }

}
