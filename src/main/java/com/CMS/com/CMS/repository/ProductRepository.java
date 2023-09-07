package com.CMS.com.CMS.repository;

import com.CMS.com.CMS.pojo.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product,String> {

}
