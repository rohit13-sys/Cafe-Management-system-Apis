package com.CMS.com.CMS.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Setter @Getter
@Data
public class Role extends StandardEntity{
    @Column(unique = true)
    private String role;
}
