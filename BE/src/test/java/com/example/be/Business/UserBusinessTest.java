package com.example.be.Business;

import com.example.be.Business.exception.InvalidUserException;
import com.example.be.Business.userUseCases.userUseCasesImpl.*;
import com.example.be.Domain.User.CreateUserRequest;
import com.example.be.Domain.User.CreateUserResponse;
import com.example.be.Domain.User.UpdateUserRequest;
import com.example.be.Domain.User.User;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserBusinessTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;
    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;
    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;


    @Test
    void createUser_ValidRequest_CreatesUser() {
        // Arrange
        CreateUserRequest request = CreateUserRequest.builder()
                .name("John Doe")
                .password("password")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("John Doe")
                .build();

        String encodedPassword = "encodedPassword";

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        // Act
        CreateUserResponse response = createUserUseCase.createUser(request);

        // Assert
        assertEquals(1L, response.getUserId());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_ValidRequest_UpdatesUser() {
        // Arrange

        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(1L)
                .name("Updated Name")
                .build();

        UserEntity existingUser = UserEntity.builder()
                .id(1L)
                .username("Old Name")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Act
        updateUserUseCase.updateUser(request);

        // Assert
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void updateUser_InvalidUserId_ThrowsException() {
        // Arrange
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(1L)
                .name("Updated Name")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> updateUserUseCase.updateUser(request));
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void getUser_ExistingUserId_ReturnsUser() {
        // Arrange
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("John Doe")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        Optional<User> result = getUserUseCase.getUser(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUser_NonExistingUserId_ReturnsEmpty() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = getUserUseCase.getUser(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void deleteUser_ValidUserId_DeletesUser() {
        // Arrange
        long UserId = 1L;
        when(userRepository.existsById(UserId)).thenReturn(true);

        // Act
        deleteUserUseCase.deleteUser(UserId);

        // Assert
        verify(userRepository, times(1)).deleteById(UserId);
    }

    @Test
    void deleteUser_InvalidUserId_ThrowsException() {
        // Arrange
        long UserId = 1L;
        when(userRepository.existsById(UserId)).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidUserException.class, () -> deleteUserUseCase.deleteUser(UserId));
        verify(userRepository, never()).deleteById(UserId);
    }
}
