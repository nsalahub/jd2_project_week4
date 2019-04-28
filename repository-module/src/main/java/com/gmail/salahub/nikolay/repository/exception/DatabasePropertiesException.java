package com.gmail.salahub.nikolay.repository.exception;

import java.sql.SQLException;

public class DatabasePropertiesException extends RuntimeException {
    public DatabasePropertiesException(String message, Throwable e) {
        super(message,e);
    }
}
