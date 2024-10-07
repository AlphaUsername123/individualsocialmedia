package com.example.be.Business.userUseCases.userUseCasesImpl;

import com.example.be.Business.userUseCases.CreateUserUseCase;
import com.example.be.Domain.User.CreateUserRequest;
import com.example.be.Domain.User.CreateUserResponse;
import com.example.be.Repository.PostsRepository;
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

    private final PostsRepository postsRepository;

    private final UserRepository userRepository;

    // TODO: add password encoder field
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        UserEntity savedUser= saveNewUser(request);

        saveNewUser(savedUser, request.getPassword());

        return CreateUserResponse.builder()
                .UserId(savedUser.getId())
                .build();
    }

    private void saveNewUser(UserEntity user, String password) {
        // TODO: uncomment lines below
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername().toString() + USERNAME_SUFFIX)
                .password(encodedPassword)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));
        userRepository.save(newUser);
    }

    private UserEntity saveNewUser(CreateUserRequest request) {
        UserEntity newUser = UserEntity.builder()
                .username(request.getName())
                .build();
        return userRepository.save(newUser);
    }
}
