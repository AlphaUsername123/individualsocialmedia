package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.exception.InvalidUserException;
import com.example.be.Business.userUseCases.DeleteUserUseCase;
import com.example.be.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void deleteUser(long customerId) {
        if (!userRepository.existsById(customerId)) {
            throw new InvalidUserException("USER_ID_INVALID");
        }
        userRepository.deleteById(customerId);
    }
}
