package com.CMS.com.CMS.exceptions;

public class StatusUpdateException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public StatusUpdateException(String message) {
        super(message);
    }
}
