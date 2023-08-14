package com.CMS.com.CMS.pojo;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@DynamicInsert @DynamicUpdate
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "username is mandatory")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;

    @NotBlank(message = "contactNumber is mandatory")
    @Column(name = "contactNumber")
    private String contactNumber;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "status is mandatory")
    private String status;

    @NotBlank(message = "role is mandatory")
    private String role;

}
