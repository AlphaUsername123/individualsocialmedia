package com.example.be.Configuration.db;

import com.example.be.Business.userUseCases.CreateUserUseCase;
import com.example.be.Domain.User.CreateUserRequest;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class DatabaseDummyDataInitializer {

    private UserRepository userRepository;
    private PostsRepository postsRepository;
    private PasswordEncoder passwordEncoder;
    private CreateUserUseCase createUserUseCase;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void populateDatabaseInitialDummyData() {
        if (isDatabaseEmpty()) {
            insertMODERATORUser();
            insertUser();
            insertProducts();
        }
    }

    private boolean isDatabaseEmpty() {
        return userRepository.count() == 0;
    }

    private void insertMODERATORUser() {
        UserEntity MODERATORUser = UserEntity.builder()
                .username("MODERATOR@fontys.nl")
                .password(passwordEncoder.encode("test123"))
                .build();
        UserRoleEntity MODERATORRole = UserRoleEntity.builder().role(RoleEnum.MODERATOR).user(MODERATORUser).build();
        MODERATORUser.setUserRoles(Set.of(MODERATORRole));
        userRepository.save(MODERATORUser);
    }

    private void insertUser() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .password("test123")
                .name("Linda")
                .build();
        createUserUseCase.createUser(createUserRequest);
    }

    private void insertProducts()
    {
        PostEntity post = PostEntity.builder()
                .text("Milk")
                .build();

        PostEntity post1 = PostEntity.builder()
                .text("something")
                .build();

        PostEntity post2 = PostEntity.builder()
                .text("lol")
                .build();

        postsRepository.save(post);
        postsRepository.save(post1);
        postsRepository.save(post2);
    }
}
