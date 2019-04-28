package com.gmail.salahub.nikolay.repository.exception;

import java.sql.SQLException;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(String message, Throwable e) {
        super(message, e);
    }
}
