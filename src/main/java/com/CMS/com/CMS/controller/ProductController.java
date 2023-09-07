package com.CMS.com.CMS.controller;

import com.CMS.com.CMS.pojo.Product;
import com.CMS.com.CMS.repository.ProductRepository;
import com.CMS.com.CMS.service.IGenericService;
import com.CMS.com.CMS.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;


    @PostMapping
    @Secured("ADMIN")
    public Product createProduct(@RequestBody Product product){
        return service.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return service.findAll();
    }
}
