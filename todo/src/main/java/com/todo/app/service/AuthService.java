package com.todo.app.service;

import com.todo.app.dto.LoginDto;
import com.todo.app.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}