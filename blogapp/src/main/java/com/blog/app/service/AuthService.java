package com.blog.app.service;


import com.blog.app.dto.LoginDto;
import com.blog.app.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
