package com.CMS.com.CMS.wrapper;

import com.CMS.com.CMS.pojo.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserWrapper {

    private String id;
    private String username;
    private String contactNumber;
    private String email;
    private String status;
    private List<Role> roles;

}
