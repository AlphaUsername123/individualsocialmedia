package com.example.be.Business.postUseCases.impl;


import com.example.be.Business.postUseCases.GetPostsUseCase;
import com.example.be.Domain.Posts.GetAllPostsRequest;
import com.example.be.Domain.Posts.GetAllPostsResponse;
import com.example.be.Domain.Posts.Post;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.entity.PostEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetPostsUseCaseImpl implements GetPostsUseCase {
    private PostsRepository postsRepository;

    @Override
    public GetAllPostsResponse getPosts(final GetAllPostsRequest request) {
        List<PostEntity> results = postsRepository.findAll();

        final GetAllPostsResponse response = new GetAllPostsResponse();
        List<Post> PostsDTO = results
                .stream()
                .map(PostConverter::convert)
                .toList();
        response.setPosts(PostsDTO);

        return response;
    }
}
