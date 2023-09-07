package com.CMS.com.CMS.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class ItemCategory extends StandardEntity{


    @Column(unique = true)
    private String name;

    private String description;
}
