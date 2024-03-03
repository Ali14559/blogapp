package com.upahar.ali.authService;

import com.upahar.ali.dto.LoginDto;
import com.upahar.ali.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
