package com.example.be.Business.banUseCases;

import com.example.be.Domain.BanUser.BanUserRequest;

public interface BanUserUseCase {
    void execute(BanUserRequest request);
}
