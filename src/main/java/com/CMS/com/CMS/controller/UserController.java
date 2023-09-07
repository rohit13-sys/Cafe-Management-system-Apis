package com.CMS.com.CMS.controller;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.service.UserService;
import com.CMS.com.CMS.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')"
    @Secured("ADMIN")
    public ResponseEntity<List<UserWrapper>> getAllUsers() {return service.getAllUsers();}


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {return service.loginUser(user);}

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody Role role){
        return service.createRole(role);
    }
}
