package com.blog.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogApiException extends RuntimeException {
    public BlogApiException(String commentNotFound) {
        super(commentNotFound);
    }
}
