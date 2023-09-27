package com.CMS.com.CMS.JWT;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    private com.CMS.com.CMS.pojo.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = repo.findByUsername(username);
        if (!Objects.isNull(userDetail)) {
            List<Role> roles=userDetail.getRoles();
            List<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
                return new SimpleGrantedAuthority(r.getRole());
            }).collect(Collectors.toList());
            return new User(userDetail.getUsername()
                    , userDetail.getPassword(),grantedAuthorities);
        } else {
            return null;
        }
    }

    public com.CMS.com.CMS.pojo.User getUserDetail(){
        return userDetail;
    }

    public boolean isAdmin(){
        com.CMS.com.CMS.pojo.User loggedInUser=getUserDetail();
        if(loggedInUser.getRoles().get(0).getRole().equalsIgnoreCase("ADMIN")){
            return true;
        }else{
            return false;
        }
    }
}
