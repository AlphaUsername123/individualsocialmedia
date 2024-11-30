package com.example.be.Business.banUseCases.impl;

import com.example.be.Business.banUseCases.UnbanUserUseCase;
import com.example.be.Business.exception.UserNotBannedException;
import com.example.be.Repository.UserBanRepository;
import com.example.be.Repository.entity.UserBanEntity;
import org.springframework.stereotype.Service;

@Service
public class UnbanUserUseCaseImpl implements UnbanUserUseCase {
    private UserBanRepository userBanRepository;

    @Override
    public void execute(Long userId) {
        UserBanEntity userBanEntity = userBanRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotBannedException(userId));

        userBanRepository.delete(userBanEntity);
    }
}
