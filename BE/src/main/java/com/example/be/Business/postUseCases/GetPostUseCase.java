package com.example.be.Business.postUseCases;

import com.example.be.Domain.Posts.Post;

import java.util.Optional;

public interface GetPostUseCase {
    Optional<Post> getPost(long PostId);
}
