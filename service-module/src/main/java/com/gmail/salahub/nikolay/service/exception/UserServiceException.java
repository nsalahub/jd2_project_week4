package com.gmail.salahub.nikolay.service.exception;

import java.sql.SQLException;

public class UserServiceException extends RuntimeException {
    public UserServiceException(String message, Throwable e) {
        super(message, e);
    }
}
