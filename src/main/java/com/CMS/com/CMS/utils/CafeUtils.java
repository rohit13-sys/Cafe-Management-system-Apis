package com.CMS.com.CMS.utils;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

public class CafeUtils {

    private CafeUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status) {
        return new ResponseEntity<>("{\"message\": \"" + responseMessage + "\"}", status);
    }

}
