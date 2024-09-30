package com.example.be.Business.postUseCases;

import com.example.be.Domain.Posts.GetAllPostsRequest;
import com.example.be.Domain.Posts.GetAllPostsResponse;

public interface GetPostsUseCase {
    GetAllPostsResponse getPosts(GetAllPostsRequest request);
}
