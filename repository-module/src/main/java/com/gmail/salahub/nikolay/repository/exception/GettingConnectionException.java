package com.gmail.salahub.nikolay.repository.exception;

import java.sql.SQLException;

public class GettingConnectionException extends RuntimeException {
    public GettingConnectionException(String message, Throwable e) {
        super(message, e);
    }
}
