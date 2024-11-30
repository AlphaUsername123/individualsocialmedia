package com.example.be.Business;

import com.example.backend.Business.exception.InvalidCredentialsException;
import com.example.backend.Business.impl.LoginUseCaseImpl;
import com.example.backend.Configuration.security.token.AccessTokenEncoder;
import com.example.backend.Configuration.security.token.impl.AccessTokenImpl;
import com.example.backend.Domain.LoginRequest;
import com.example.backend.Domain.LoginResponse;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Repository.entity.CustomerEntity;
import com.example.backend.Repository.entity.RoleEnum;
import com.example.backend.Repository.entity.UserEntity;
import com.example.backend.Repository.entity.UserRoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        when(user.getCustomer()).thenReturn(null);

        // Create mock UserRole objects and set them to return in the user roles list
        UserRoleEntity mockCustomerRole = mock(UserRoleEntity.class);
        UserRoleEntity mockMODERATORRole = mock(UserRoleEntity.class);
        when(mockCustomerRole.getRole()).thenReturn(RoleEnum.CUSTOMER);
        when(mockMODERATORRole.getRole()).thenReturn(RoleEnum.MODERATOR);
        when(user.getUserRoles()).thenReturn(Set.of(mockCustomerRole, mockMODERATORRole));

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
    void testLoginSuccessWithCustomer() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        CustomerEntity customer = mock(CustomerEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        String expectedToken = "accessToken";
        when(user.getUsername()).thenReturn("username");
        when(user.getCustomer()).thenReturn(customer);
        when(customer.getId()).thenReturn(1L);

        // Create UserRoleEntity objects and set them to return in the user roles set
        UserRoleEntity customerRole = UserRoleEntity.builder().role(RoleEnum.CUSTOMER).build();
        UserRoleEntity MODERATORRole = UserRoleEntity.builder().role(RoleEnum.MODERATOR).build();
        when(user.getUserRoles()).thenReturn(Set.of(customerRole, MODERATORRole));

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
    void testLoginSuccessWithoutCustomer() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserEntity user = mock(UserEntity.class);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);

        String expectedToken = "accessToken";
        when(user.getUsername()).thenReturn("username");
        when(user.getCustomer()).thenReturn(null);

        // Create UserRoleEntity objects and set them to return in the user roles set
        UserRoleEntity customerRole = UserRoleEntity.builder().role(RoleEnum.CUSTOMER).build();
        UserRoleEntity MODERATORRole = UserRoleEntity.builder().role(RoleEnum.MODERATOR).build();
        when(user.getUserRoles()).thenReturn(Set.of(customerRole, MODERATORRole));

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
