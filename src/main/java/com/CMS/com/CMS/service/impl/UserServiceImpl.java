package com.CMS.com.CMS.service.impl;

import com.CMS.com.CMS.JWT.CustomUserDetailsService;
import com.CMS.com.CMS.JWT.JWTUtil;
import com.CMS.com.CMS.JWT.JwtAuthenticationFilter;
import com.CMS.com.CMS.constants.CafeConstants;
import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.repository.UserRepository;
import com.CMS.com.CMS.service.IUserService;
import com.CMS.com.CMS.utils.CafeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    @SneakyThrows
    public ResponseEntity<String> createUser(User user) {
        log.info("Inside SignUp {}", user);
        repo.save(user);
        return CafeUtils.getResponseEntity(CafeConstants.CREATED, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> loginUser(User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        if (auth.isAuthenticated()) {
            if (customUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("active")) {
                String jwtToken = jwtUtil.generateToken(customUserDetailsService.getUserDetail().getEmail(), customUserDetailsService.getUserDetail().getRole());
                return new ResponseEntity<>("{\"token\":\"" + jwtToken + "\"}", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Please wait for admin approval", HttpStatus.BAD_REQUEST);
            }
        }

        return CafeUtils.getResponseEntity(CafeConstants.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }
}
