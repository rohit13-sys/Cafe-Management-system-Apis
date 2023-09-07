package com.CMS.com.CMS.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Product extends StandardEntity {

    @Column(unique = true)
    private String name;

    private float price;

    private long stock;

    @ManyToOne
    private ItemCategory category;
}
