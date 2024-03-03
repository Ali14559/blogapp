package com.upahar.ali.exception;

import org.springframework.http.HttpStatus;

public class TodoAPIException extends RuntimeException {
    public TodoAPIException(String message) {
        super(message);
    }
}
