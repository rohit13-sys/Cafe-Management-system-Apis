package com.CMS.com.CMS.controller;

import com.CMS.com.CMS.pojo.ItemCategory;
import com.CMS.com.CMS.pojo.Product;
import com.CMS.com.CMS.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    @Secured("ADMIN")
    public ItemCategory createCategory(@RequestBody ItemCategory category){return service.save(category);}

    @GetMapping
    public List<ItemCategory> getAllCategories(){
        return service.findAll();
    }
}
