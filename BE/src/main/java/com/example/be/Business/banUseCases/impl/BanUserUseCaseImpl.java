package com.example.be.Business.banUseCases.impl;

import com.example.be.Business.banUseCases.BanUserUseCase;
import com.example.be.Business.exception.UserNotFoundException;
import com.example.be.Domain.BanUser.BanUserRequest;
import com.example.be.Repository.UserBanRepository;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.UserBanEntity;
import com.example.be.Repository.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class BanUserUseCaseImpl implements BanUserUseCase {
    private UserRepository userRepository;

    private UserBanRepository userBanRepository;

    @Override
    public void execute(BanUserRequest request) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        UserBanEntity userBan = new UserBanEntity();
        userBan.setUser(userEntity);
        userBan.setReason(request.getReason());
        userBan.setBanUntil(request.getBanUntil());

        userBanRepository.save(userBan);
    }
}
