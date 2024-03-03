package com.blog.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}