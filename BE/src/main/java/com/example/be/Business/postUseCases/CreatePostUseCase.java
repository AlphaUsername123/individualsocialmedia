package com.example.be.Business.postUseCases;
import com.example.be.Domain.Posts.CreatePostRequest;
import com.example.be.Domain.Posts.CreatePostResponse;

public interface CreatePostUseCase {
    CreatePostResponse createPost(CreatePostRequest request);
}
