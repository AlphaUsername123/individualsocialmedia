//package com.example.be;
//
//import com.example.be.Repository.entity.PostEntity;
//import com.example.be.Repository.PostsRepository;
//import com.example.be.Services.PostsService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class PostsServiceTest {
//    @Mock
//    private PostsRepository repo;
//
//    private PostsService postsService;
//
//    @BeforeEach
//    public void setUp()
//    {
//        MockitoAnnotations.initMocks(this);
//        postsService = new PostsService(repo);
//    }
//
//    @Test
//    public void testCreatePost()
//    {
//        //Arrange
//        PostEntity postEntity = PostEntity.builder()
//                .id(1)
//                .text("Something about a post")
//                .build();
//        when(repo.findById(1L)).thenReturn(Optional.of(postEntity));
//
//        // Act
//        PostEntity retrievedPostEntity = postsService.getById(1);
//
//        // Assert
//        verify(repo).findById(1L);
//        assertEquals(postEntity, retrievedPostEntity);
//    }
//
//    @Test
//    public void testFindAllPosts() {
//        // Arrange
//        PostEntity postEntity1 = PostEntity.builder()
//                .id(1)
//                .text("Something lol")
//                .build();
//
//        PostEntity postEntity2 = PostEntity.builder()
//                .id(2)
//                .text("Something lol124")
//                .build();
//
//        List<PostEntity> postsList = Arrays.asList(postEntity1, postEntity2);
//
//        when(repo.findAll()).thenReturn(postsList);
//
//        // Act
//        List<PostEntity> savedPostEntities = postsService.getAll();
//
//        // Assert
//        verify(repo).findAll(); // Ensure the repo's findAll method was called
//        assertEquals(2, savedPostEntities.size()); // Ensure we got 2 posts back
//        assertEquals(postEntity1.getText(), savedPostEntities.get(0).getText()); // Validate first post text
//        assertEquals(postEntity2.getText(), savedPostEntities.get(1).getText()); // Validate second post text
//    }
//}fgfgfgf
