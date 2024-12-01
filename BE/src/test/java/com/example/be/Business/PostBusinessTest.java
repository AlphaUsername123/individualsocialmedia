package com.example.be.Business;

import com.example.be.Business.exception.PostAlreadyExistsException;
import com.example.be.Business.postUseCases.impl.*;
import com.example.be.Domain.Posts.*;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostBusinessTest {

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private CreatePostUseCaseImpl createPostUseCase;

    @InjectMocks
    private DeletePostUseCaseImpl deletePostUseCase;

    @InjectMocks
    private GetPostsUseCaseImpl getPostsUseCase;

    @InjectMocks
    private GetPostUseCaseImpl getPostUseCase;

    @InjectMocks
    private UpdatePostUseCaseImpl updatePostUseCase;

    @Test
    public void testCreatePost_Success() {
        // Arrange
        CreatePostRequest request = new CreatePostRequest();
        request.setText("New Post");

        PostEntity savedPost = new PostEntity();
        savedPost.setId(1); // Assuming the ID type is Integer
        savedPost.setText("New Post");

        when(postsRepository.save(any(PostEntity.class))).thenReturn(savedPost);

        // Act
        CreatePostResponse response = createPostUseCase.createPost(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getId());
        verify(postsRepository).save(any(PostEntity.class));
    }

    @Test
    public void testDeletePost_Success() {
        // Arrange
        long PostId = 1L;
        when(postsRepository.existsById(PostId)).thenReturn(true);

        // Act
        deletePostUseCase.deletePost(PostId);

        // Assert
        verify(postsRepository, times(1)).deleteById(PostId);
    }

    @Test
    public void testGetPosts_Success() {
        // Arrange
        GetAllPostsRequest request = new GetAllPostsRequest();

        PostEntity PostEntity1 = new PostEntity();
        PostEntity1.setId(1);
        PostEntity1.setText("Post 1");

        PostEntity PostEntity2 = new PostEntity();
        PostEntity2.setId(2);
        PostEntity2.setText("Post 2");

        List<PostEntity> PostEntities = Arrays.asList(PostEntity1, PostEntity2);
        when(postsRepository.findAll()).thenReturn(PostEntities);

        // Actp
        GetAllPostsResponse response = getPostsUseCase.getPosts(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getPosts());
        assertEquals(2, response.getPosts().size());

        Post Post1 = response.getPosts().get(0);
        assertEquals(1, Post1.getId());
        assertEquals("Post 1", Post1.getText());

        Post Post2 = response.getPosts().get(1);
        assertEquals(2, Post2.getId());
        assertEquals("Post 2", Post2.getText());
    }

    @Test
    public void testGetPost_Success() {
        // Arrange
        int PostId = 1;
        PostEntity PostEntity = new PostEntity();
        PostEntity.setId(PostId);
        PostEntity.setText("Test Post");

        when(postsRepository.findById((long)PostId)).thenReturn(Optional.of(PostEntity));

        // Act
        Optional<Post> PostOptional = getPostUseCase.getPost(PostId);

        // Assert
        assertTrue(PostOptional.isPresent());
        Post Post = PostOptional.get();
        assertEquals(PostId, Post.getId());
        assertEquals("Test Post", Post.getText());
    }

    @Test
    public void testUpdatePost_Success() {
        // Arrange
        long PostId = 1L;
        UpdatePostRequest request = new UpdatePostRequest();
        request.setId(PostId);
        request.setText("Updated Title");

        PostEntity PostEntity = new PostEntity();
        PostEntity.setId((int)PostId);
        PostEntity.setText("Original Title");

        when(postsRepository.findById(PostId)).thenReturn(Optional.of(PostEntity));

        // Act
        updatePostUseCase.updatePost(request);

        // Assert
        verify(postsRepository).save(PostEntity);
        verify(postsRepository).findById(PostId);
    }

    @Test
    public void testUpdatePost_NotFound() {
        // Arrange
        long PostId = 2L;
        UpdatePostRequest request = new UpdatePostRequest();
        request.setId(PostId);
        request.setText("Non-existent Post");

        when(postsRepository.findById(PostId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostAlreadyExistsException.class, () -> {
            updatePostUseCase.updatePost(request);
        });
    }
}
