package com.gmail.salahub.nikolay.repository.exception;

import java.io.IOException;

public class InitialFileReadingException extends RuntimeException {
    public InitialFileReadingException(String message, Throwable e) {
        super(message, e);
    }
}
