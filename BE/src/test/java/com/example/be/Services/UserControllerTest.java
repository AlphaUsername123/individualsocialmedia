package com.example.be.Services;


import com.example.be.Business.userUseCases.*;
import com.example.be.Domain.User.*;
import com.example.be.Repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserUseCase getUserUseCase;
    @MockBean
    private GetUsersUseCase getUsersUseCase;
    @MockBean
    private DeleteUserUseCase deleteUserUseCase;
    @MockBean
    private CreateUserUseCase createUserUseCase;
    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"User"})
    void getUser_shouldReturn200WithUser_whenUserFound() throws Exception {
        User user = User.builder()
                .name("Rivaldo Vítor Borba Ferreira")
                .id(10L)
                .build();
        when(getUserUseCase.getUser(10L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/Users/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                           {"id":10, "name":"Rivaldo Vítor Borba Ferreira"}
                        """));

        verify(getUserUseCase).getUser(10L);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"User"})
    void getUser_shouldReturn404Error_whenUserNotFound() throws Exception {
        when(getUserUseCase.getUser(10L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/Users/10"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getUserUseCase).getUser(10L);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void getAllUsers_shouldReturn200WithUsersList() throws Exception {
        GetAllUsersResponse responseDTO = GetAllUsersResponse.builder()
                .Users(List.of(
                        User.builder().id(1L).name("Romario").build(),
                        User.builder().id(1L).name("Ronaldo").build()
                ))
                .build();
        GetAllUsersRequest request = GetAllUsersRequest.builder().build();
        when(getUsersUseCase.getUsers(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/Users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "users":[
                                    {"id":1, "name":"Romario"},
                                    {"id":1, "name":"Ronaldo"}
                                ]
                            }
                        """));

        verify(getUsersUseCase).getUsers(request);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void deleteUser_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/Users/100"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).deleteUser(100L);
    }

    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void createUser_shouldReturn201_whenRequestIsValid() throws Exception {
        CreateUserRequest expectedRequest = CreateUserRequest.builder()
                .password("test123")
                .name("James")
                .build();
        when(createUserUseCase.createUser(expectedRequest))
                .thenReturn(CreateUserResponse.builder()
                        .UserId(200L)
                        .build());

        mockMvc.perform(post("/Users")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "James",
                                    "password": "test123"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            { "userId":  200 }
                        """));

        verify(createUserUseCase).createUser(expectedRequest);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"User"})
    void updateUser_shouldReturn204() throws Exception {
        mockMvc.perform(put("/Users/100")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "Daniel"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        UpdateUserRequest expectedRequest = UpdateUserRequest.builder()
                .id(100L)
                .name("Daniel")
                .build();
        verify(updateUserUseCase).updateUser(expectedRequest);
    }
}