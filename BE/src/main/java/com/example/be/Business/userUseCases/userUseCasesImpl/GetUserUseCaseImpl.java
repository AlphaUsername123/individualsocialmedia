package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.exception.UnauthorizedDataAccessException;
import com.example.be.Business.userUseCases.GetUserUseCase;
import com.example.be.Configuration.security.token.AccessToken;
import com.example.be.Domain.User.User;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;
    private AccessToken requestAccessToken;

    @Override
    public Optional<User> getUser(long userId) {
       if (!requestAccessToken.hasRole(RoleEnum.MODERATOR.name())) {

              throw new UnauthorizedDataAccessException("STUDENT_ID_NOT_FROM_LOGGED_IN_USER");
      }

        return userRepository.findById(userId).map(UserConverter::convert);
    }
}
