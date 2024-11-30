package com.example.be.Services;

import com.example.be.Business.postUseCases.*;
import com.example.be.Domain.Posts.*;
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
class PostServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePostUseCase createPostUseCase;

    @MockBean
    private UpdatePostUseCase updatePostUseCase;

    @MockBean
    private GetPostUseCase getPostUseCase;

    @MockBean
    private GetPostsUseCase getPostsUseCase;

    @MockBean
    private DeletePostUseCase deletePostUseCase;


    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void createPost_shouldReturn201_whenRequestIsValid() throws Exception {
        CreatePostRequest expectedRequest = CreatePostRequest.builder()
                .text("very good, very nice!")
                .build();
        when(createPostUseCase.createPost(expectedRequest))
                .thenReturn(CreatePostResponse.builder()
                        .id(1)
                        .build());

        mockMvc.perform(post("/Posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "text": "very good, very nice!"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                            { "id":  1 }
                        """));

        verify(createPostUseCase).createPost(expectedRequest);
    }

    @Test
    void updatePost_shouldReturn204() throws Exception {
        mockMvc.perform(put("/Posts/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "text": "Milk"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isNoContent());

        UpdatePostRequest expectedRequest = UpdatePostRequest.builder()
                .id(1L)
                .text("Milk")
                .build();
        verify(updatePostUseCase).updatePost(expectedRequest);
    }

    @Test
    void getPost_shouldReturn200WithPost_whenPostFound() throws Exception {
        Post post = Post.builder()
                .text("Bread")
                .id(10)
                .build();
        when(getPostUseCase.getPost(10)).thenReturn(Optional.of(Post));

        mockMvc.perform(get("/Posts/10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {"id": 10, "text":"Bread"}
                        """));

        verify(getPostUseCase).getPost(10);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"USER, MODERATOR"})
    void getPost_shouldReturn404Error_whenPostNotFound() throws Exception {
        when(getPostUseCase.getPost(10)).thenReturn(Optional.empty());

        mockMvc.perform(get("/Posts/10"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getPostUseCase).getPost(10);
    }

    @Test
    @WithMockUser(username = "10@fontys.nl", roles = {"CUSTOMER, MODERATOR"})
    void getAllPosts_shouldReturn200WithPostsList_WhenNoFilterProvided() throws Exception {
        GetAllPostsResponse responseDTO = GetAllPostsResponse.builder()
                .Posts(List.of(
                        Post.builder().id(1).title("Chair").description("wood made").price(50.50).build(),
                        Post.builder().id(2).title("Table").description("glass made").price(100.00).build()
                ))
                .build();
        GetAllPostsRequest request = GetAllPostsRequest.builder().build();
        when(getPostsUseCase.getPosts(request)).thenReturn(responseDTO);

        mockMvc.perform(get("/Posts/getall"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                            {
                                "Posts":[
                                    {"id":1, "title":"Chair", "price": 50.50, "description": "wood made"},
                                    {"id":2, "title":"Table", "price": 100.00, "description": "glass made"}
                                ]
                            }
                        """));

        verify(getPostsUseCase).getPosts(request);
    }


    @Test
    @WithMockUser(username = "MODERATOR@fontys.nl", roles = {"MODERATOR"})
    void deleteStudent_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/Posts/100"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(deletePostUseCase).deletePost(100L);
    }
}
