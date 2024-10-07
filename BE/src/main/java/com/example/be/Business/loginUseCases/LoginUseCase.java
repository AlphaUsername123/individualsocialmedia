package com.example.be.Business.loginUseCases;

import com.example.be.Domain.LoginRequest;
import com.example.be.Domain.LoginResponse;

public interface LoginUseCase
{
    LoginResponse login(LoginRequest loginRequest);
}
