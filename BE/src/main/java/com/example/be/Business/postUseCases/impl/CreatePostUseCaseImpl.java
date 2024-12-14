package com.example.be.Business.postUseCases.impl;


import com.example.be.Business.postUseCases.CreatePostUseCase;
import com.example.be.Domain.Posts.CreatePostRequest;
import com.example.be.Domain.Posts.CreatePostResponse;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.PostEntity;
import com.example.be.Repository.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {

        PostEntity savedPost = saveNewPost(request);

        return CreatePostResponse.builder()
                .id(savedPost.getId())
                .build();
    }

    private PostEntity saveNewPost(CreatePostRequest request) {
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        PostEntity newPost = PostEntity.builder()
                .text(request.getText())
                .user(userEntity.get())
                .createdAt(request.getCreatedAt())
                .build();
        return postsRepository.save(newPost);
    }
}
