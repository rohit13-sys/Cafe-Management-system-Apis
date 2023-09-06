package com.CMS.com.CMS.service.impl;

import com.CMS.com.CMS.JWT.CustomUserDetailsService;
import com.CMS.com.CMS.JWT.JWTUtil;
import com.CMS.com.CMS.constants.CafeConstants;
import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.repository.RoleRepository;
import com.CMS.com.CMS.repository.UserRepository;
import com.CMS.com.CMS.service.IUserService;
import com.CMS.com.CMS.utils.CafeUtils;
import com.CMS.com.CMS.wrapper.UserWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ObjectMapper mapper;


    @Override
    @SneakyThrows
    public ResponseEntity<String> createUser(User user) {
        log.info("Inside SignUp {}", user);
        List<Role> roles=new ArrayList<>();
        if(!user.getRoles().isEmpty()){
//            List<Role> roleDtos=new ArrayList<>();
            for(Role roleDto: user.getRoles()){
                Role role=roleRepo.findByRole(roleDto.getRole());
                roles.add(role);
            }
        }
        user.setRoles(roles);
        userRepo.save(user);
        return CafeUtils.getResponseEntity(CafeConstants.CREATED, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        List<User> users= userRepo.findAll();
        List<UserWrapper> response=users.stream().map(this::convertUserToUSerWrapper).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private UserWrapper convertUserToUSerWrapper(User user) {
        return mapper.convertValue(user, UserWrapper.class);
    }

    @Override
    public ResponseEntity<String> loginUser(User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (auth.isAuthenticated()) {
            if (customUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("active")) {


                String jwtToken = jwtUtil.generateToken(customUserDetailsService.getUserDetail().getEmail());
                return new ResponseEntity<>("{\"token\":\"" + jwtToken + "\"}", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Please wait for admin approval", HttpStatus.BAD_REQUEST);
            }
        }

        return CafeUtils.getResponseEntity(CafeConstants.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);}

    @Override
    public ResponseEntity<String> createRole(Role role) {
        roleRepo.save(role);
        return CafeUtils.getResponseEntity("Role with name : "+role.getRole()+" "+CafeConstants.CREATED,HttpStatus.CREATED);
    }


}
