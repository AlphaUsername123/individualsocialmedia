package com.example.be.Services;

import com.example.be.Business.loginUseCases.LoginUseCase;
import com.example.be.Domain.LoginRequest;
import com.example.be.Domain.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUseCase loginUseCase;

    @Test
    void login_ValidRequest_ReturnsCreatedStatus() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("user")
                .password("pass")
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken("dummy-token")
                .build();

        when(loginUseCase.login(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/login/auth")
                        .contentType("application/json")
                        .content("""
                                {
                                    "username": "user",
                                    "password": "pass"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "accessToken": "dummy-token"
                        }
                        """));

        verify(loginUseCase).login(any(LoginRequest.class));
    }

    @Test
    void login_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/login/auth")
                        .contentType("application/json")
                        .content("""
                                {
                                    "username": "",
                                    "password": ""
                                }
                                """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
