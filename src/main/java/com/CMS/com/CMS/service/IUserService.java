package com.CMS.com.CMS.service;

import com.CMS.com.CMS.pojo.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {
    public ResponseEntity<String> createUser(User user);

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<String> loginUser(User user);
}
