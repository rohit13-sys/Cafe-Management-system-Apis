package com.CMS.com.CMS.service;

import com.CMS.com.CMS.JWT.CustomUserDetailsService;
import com.CMS.com.CMS.JWT.JWTUtil;
import com.CMS.com.CMS.constants.CafeConstants;
import com.CMS.com.CMS.exceptions.StatusUpdateException;
import com.CMS.com.CMS.pojo.EmailDetails;
import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import com.CMS.com.CMS.repository.RoleRepository;
import com.CMS.com.CMS.repository.UserRepository;
import com.CMS.com.CMS.utils.CafeUtils;
import com.CMS.com.CMS.utils.EmailServiceImpl;
import com.CMS.com.CMS.wrapper.UserWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.ResourceAccessException;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends GenericService<User> {

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

    @Autowired
    private EmailServiceImpl emailService;

    public UserService(UserRepository repository) {
        super(repository);
        this.userRepo = repository;
    }


    @SneakyThrows
    public ResponseEntity<String> createUser(User user) {
        log.info("Inside SignUp {}", user);
        List<Role> roles = new ArrayList<>();
        if (!user.getRoles().isEmpty()) {
//            List<Role> roleDtos=new ArrayList<>();
            for (Role roleDto : user.getRoles()) {
                Role role = roleRepo.findByRole(roleDto.getRole());
                roles.add(role);
            }
        }
        user.setRoles(roles);
        userRepo.save(user);
        return CafeUtils.getResponseEntity(CafeConstants.CREATED, HttpStatus.CREATED);

    }


    public ResponseEntity<List<UserWrapper>> getAllUsers(Specification<User> specs) {
        List<User> users = userRepo.findAll(Specification.where(specs));
        List<UserWrapper> response = users.stream().map(this::convertUserToUSerWrapper).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private UserWrapper convertUserToUSerWrapper(User user) {
        return mapper.convertValue(user, UserWrapper.class);
    }

    public ResponseEntity<String> loginUser(User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (auth.isAuthenticated()) {
            if (customUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                String jwtToken = jwtUtil.generateToken(customUserDetailsService.getUserDetail().getUsername());
                return new ResponseEntity<>("{\"token\":\"" + jwtToken + "\"}", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity("Please wait for admin approval", HttpStatus.BAD_REQUEST);
            }
        }
        return CafeUtils.getResponseEntity(CafeConstants.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> createRole(Role role) {
        roleRepo.save(role);
        return CafeUtils.getResponseEntity("Role with name : " + role.getRole() + " " + CafeConstants.CREATED, HttpStatus.CREATED);
    }


    @SneakyThrows
    public UserWrapper updateUserById(String userId, Map<String, String> fields) {
        Optional<User> user = Optional.ofNullable(findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found")));
        if (!customUserDetailsService.isAdmin() && fields.containsKey("status")) {
            throw new StatusUpdateException("You are not authorisd to update status of user");
        } else {
            if (fields.containsKey("status")) {
                String status = fields.get("status");
                String messageBody = "";
                if (status.equalsIgnoreCase("true")) {
                    messageBody = "User : " + user.get().getUsername() + " is approved by Admin : " + customUserDetailsService.getUserDetail().getUsername();
                } else {
                    messageBody = "User : " + user.get().getUsername() + " is disabled by Admin : " + customUserDetailsService.getUserDetail().getUsername();
                }
                sendMailAllAdmin(user.get().getUsername(), getAllAdmins(), messageBody, "Email Regarding Account Approval", "text/plain");
            }
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                if (Objects.nonNull(field)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user.get(), value);
                }
            });
        }
        return convertUserToUSerWrapper(save(user.get()));
    }

    private void sendMailAllAdmin(String recipient, List<String> cc, String messageBody, String subject, String messageType) {

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject(subject);
        emailDetails.setRecipient(recipient);
        if (!Objects.isNull(cc) && cc.size() > 0) {
            cc.remove(customUserDetailsService.getUserDetail().getUsername());
            emailDetails.setCc(cc.toArray(String[]::new));
        }
        emailDetails.setType(messageType);
        emailDetails.setMsgBody(messageBody);
        System.out.println(emailService.sendSimpleMail(emailDetails));
    }


    public List<String> getAllAdmins() {
        List<User> adminUsers = userRepo.findAllByRolesRole("ADMIN");
        return adminUsers.stream().map(User::getUsername).collect(Collectors.toList());
    }

    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    public UserWrapper changePassword(Map<String, String> fields, String userId) {
        if (!customUserDetailsService.isAdmin()) {
            User user = customUserDetailsService.getUserDetail();
            if (userId.equalsIgnoreCase(user.getId()) && fields.get("oldPassword").equals(user.getPassword()) && !fields.get("password").equals(user.getPassword())) {
                return updateUserById(userId, fields);
            } else {
                throw new ResourceAccessException("please give \n correct data in input");
            }
        }
        return updateUserById(userId, fields);
    }

    public ResponseEntity<String> forgotPassword(String username) {
        User user = userRepo.findByUsername(username);
        if (Objects.nonNull(user)) {
            String messagebody = "<h3>Your Login Details are given below</h3>" +
                    "<br><b>Username</b> : "+user.getUsername()+
                   "<br><b>Password</b> : "+user.getPassword()+
                   "<br><b>Click</b><a href=\"http://localhost:4200\">here</a> <b> to Login!!!!!</b>";
            sendMailAllAdmin(user.getUsername(), null, messagebody, "Your Password","text/html" );
        }
        return CafeUtils.getResponseEntity("Check your email for credentials", HttpStatus.ACCEPTED);
    }
}
