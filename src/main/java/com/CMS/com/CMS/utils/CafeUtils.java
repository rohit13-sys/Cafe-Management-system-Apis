package com.CMS.com.CMS.utils;

import com.CMS.com.CMS.JWT.CustomUserDetailsService;
import com.CMS.com.CMS.JWT.JWTUtil;
import com.CMS.com.CMS.constants.CafeConstants;
import com.CMS.com.CMS.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CafeUtils {


    private CafeUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status) {
        return new ResponseEntity<>("{\"message\": \"" + responseMessage + "\"}", status);
    }



}
