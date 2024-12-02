package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.userUseCases.CreateUserUseCase;
import com.example.be.Domain.User.CreateUserRequest;
import com.example.be.Domain.User.CreateUserResponse;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.RoleEnum;
import com.example.be.Repository.entity.UserEntity;
import com.example.be.Repository.entity.UserRoleEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private static final String USERNAME_SUFFIX = "@fontys.nl";

    private final UserRepository userRepository;

    // TODO: add password encoder field
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        UserEntity savedUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .UserId(savedUser.getId())
                .build();
    }

    private UserEntity saveNewUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .username(request.getName().toString() + USERNAME_SUFFIX)
                .password(encodedPassword)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));
       return userRepository.save(newUser);
    }
}
