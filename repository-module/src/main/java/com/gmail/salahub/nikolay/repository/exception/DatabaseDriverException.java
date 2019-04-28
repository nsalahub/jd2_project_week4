package com.gmail.salahub.nikolay.repository.exception;

public class DatabaseDriverException extends RuntimeException {
    public DatabaseDriverException(String massage, Throwable e) {
        super(massage,e);
    }
}
