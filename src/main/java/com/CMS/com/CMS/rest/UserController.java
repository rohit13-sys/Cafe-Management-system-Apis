package com.CMS.com.CMS.rest;

import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.service.IUserService;
import com.CMS.com.CMS.utils.CafeUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@Valid @RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return service.getAllUsers();
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return service.loginUser(user);
    }

}
