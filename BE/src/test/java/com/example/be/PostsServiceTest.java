package com.example.be;

import com.example.be.Domain.Post;
import com.example.be.Repository.PostsRepository;
import com.example.be.Services.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostsServiceTest {
    @Mock
    private PostsRepository repo;

    private PostsService postsService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        postsService = new PostsService(repo);
    }

    @Test
    public void testCreatePost()
    {
        //Arrange
        Post post = Post.builder()
                .id(1)
                .text("Something about a post")
                .build();
        when(repo.findById(1L)).thenReturn(Optional.of(post));

        // Act
        Post retrievedPost = postsService.getById(1);

        // Assert
        verify(repo).findById(1L);
        assertEquals(post, retrievedPost);
    }

    @Test
    public void testFindAllPosts() {
        // Arrange
        Post post1 = Post.builder()
                .id(1)
                .text("Something lol")
                .build();

        Post post2 = Post.builder()
                .id(2)
                .text("Something lol1234")
                .build();

        List<Post> postsList = Arrays.asList(post1, post2);

        when(repo.findAll()).thenReturn(postsList);

        // Act
        List<Post> savedPosts = postsService.getAll();

        // Assert
        verify(repo).findAll(); // Ensure the repo's findAll method was called
        assertEquals(2, savedPosts.size()); // Ensure we got 2 posts back
        assertEquals(post1.getText(), savedPosts.get(0).getText()); // Validate first post text
        assertEquals(post2.getText(), savedPosts.get(1).getText()); // Validate second post text
    }
}
