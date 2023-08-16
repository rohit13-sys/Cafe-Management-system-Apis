package com.CMS.com.CMS.JWT;

import com.CMS.com.CMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    private com.CMS.com.CMS.pojo.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = repo.findByEmail(username);
        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail()
                    , userDetail.getPassword(), new ArrayList<>());
        } else {
            return null;
        }
    }

    public com.CMS.com.CMS.pojo.User getUserDetail(){
        return userDetail;
    }
}
