package com.example.be.Business.userUseCases;

import com.example.be.Domain.User.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(long UserId);
}
