package com.CMS.com.CMS.service;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public ResponseEntity<String> createUser(User user);

    ResponseEntity<List<UserWrapper>> getAllUsers();

    ResponseEntity<String> loginUser(User user);

    ResponseEntity<String> createRole(Role role);
}
