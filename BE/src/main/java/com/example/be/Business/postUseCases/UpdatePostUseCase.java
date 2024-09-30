package com.example.be.Business.postUseCases;

import com.example.be.Domain.Posts.UpdatePostRequest;

public interface UpdatePostUseCase {
    void updatePost(UpdatePostRequest request);
}
