package com.gmail.salahub.nikolay.repository.exception;

import java.sql.SQLException;

public class ItemRepositoryException extends RuntimeException {
    public ItemRepositoryException(String message, Throwable e) {
        super(message, e);
    }
}
