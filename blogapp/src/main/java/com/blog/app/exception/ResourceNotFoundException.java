package com.blog.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

@AllArgsConstructor
public class ResourceNotFoundException extends  RuntimeException {
    public ResourceNotFoundException(String resourceNotFound) {
        super(resourceNotFound);
    }
}
