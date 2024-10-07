package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.exception.InvalidUserException;
import com.example.be.Business.userUseCases.UpdateUserUseCase;
import com.example.be.Domain.User.UpdateUserRequest;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> userOptional = userRepository.findById(request.getId());
        if (userOptional.isEmpty()) {
            throw new InvalidUserException("USER_ID_INVALID");
        }

        UserEntity user = userOptional.get();
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user) {
        user.setUsername(request.getName());

        userRepository.save(user);
    }
}
