package com.example.be.Business;

import com.example.be.Business.exception.InvalidCredentialsException;
import com.example.be.Business.loginUseCases.impl.LoginUseCaseImpl;
import com.example.be.Configuration.security.token.AccessTokenEncoder;
import com.example.be.Configuration.security.token.impl.AccessTokenImpl;
import com.example.be.Domain.LoginRequest;
import com.example.be.Domain.LoginResponse;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.RoleEnum;
import com.example.be.Repository.entity.UserEntity;
import com.example.be.Repository.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        String expectedToken = "accessToken";
        when(user.getUsername()).thenReturn("username");

        // Create mock UserRole objects and set them to return in the user roles list
        UserRoleEntity mockUserRole = mock(UserRoleEntity.class);
        UserRoleEntity mockMODERATORRole = mock(UserRoleEntity.class);
        when(mockUserRole.getRole()).thenReturn(RoleEnum.USER);
        when(mockMODERATORRole.getRole()).thenReturn(RoleEnum.MODERATOR);
        when(user.getUserRoles()).thenReturn(Set.of(mockUserRole, mockMODERATORRole));

        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(expectedToken);

        // Act
        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        // Assert
        assertEquals(expectedToken, loginResponse.getAccessToken());
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }


    @Test
    void testLoginUserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
    }

    @Test
    void testLoginInvalidPassword() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
    }

    @Test
    void testLoginSuccessWithUser() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        UserEntity User = mock(UserEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        String expectedToken = "accessToken";
        when(user.getUsername()).thenReturn("username");
        when(User.getId()).thenReturn(1L);

        // Create UserRoleEntity objects and set them to return in the user roles set
        UserRoleEntity UserRole = UserRoleEntity.builder().role(RoleEnum.USER).build();
        UserRoleEntity MODERATORRole = UserRoleEntity.builder().role(RoleEnum.MODERATOR).build();
        when(user.getUserRoles()).thenReturn(Set.of(UserRole, MODERATORRole));

        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(expectedToken);

        // Act
        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        // Assert
        assertEquals(expectedToken, loginResponse.getAccessToken());
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    void testLoginSuccessWithoutUser() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        String expectedToken = "accessToken";
        when(user.getUsername()).thenReturn("username");

        // Create UserRoleEntity objects and set them to return in the user roles set
        UserRoleEntity UserRole = UserRoleEntity.builder().role(RoleEnum.USER).build();
        UserRoleEntity MODERATORRole = UserRoleEntity.builder().role(RoleEnum.MODERATOR).build();
        when(user.getUserRoles()).thenReturn(Set.of(UserRole, MODERATORRole));

        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn(expectedToken);

        // Act
        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        // Assert
        assertEquals(expectedToken, loginResponse.getAccessToken());
        verify(userRepository, times(1)).findByUsername(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }
}
