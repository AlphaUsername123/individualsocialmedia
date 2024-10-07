package com.example.be.Business.userUseCases;

import com.example.be.Domain.User.CreateUserRequest;
import com.example.be.Domain.User.CreateUserResponse;

public interface CreateUserUseCase {
    CreateUserResponse createUser(CreateUserRequest request);
}
