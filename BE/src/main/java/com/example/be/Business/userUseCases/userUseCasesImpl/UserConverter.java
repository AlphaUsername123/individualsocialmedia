package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Domain.User.User;
import com.example.be.Repository.entity.UserEntity;

public class UserConverter {
    private UserConverter() {
    }

    public static User convert(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .name(user.getUsername())
                .build();
    }
}
