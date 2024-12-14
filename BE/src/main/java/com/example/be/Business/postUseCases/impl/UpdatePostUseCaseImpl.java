package com.example.be.Business.postUseCases.impl;

import com.example.be.Business.exception.PostAlreadyExistsException;
import com.example.be.Business.postUseCases.UpdatePostUseCase;
import com.example.be.Domain.Posts.UpdatePostRequest;
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
public class UpdatePostUseCaseImpl implements UpdatePostUseCase {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void updatePost(UpdatePostRequest request) {
        Optional<PostEntity> PostOptional = postsRepository.findById(request.getId());
        if (PostOptional.isEmpty()) {
            throw new PostAlreadyExistsException("Post_ID_INVALID");
        }

        PostEntity Post = PostOptional.get();
        updateFields(request, Post);
    }

    private void updateFields(UpdatePostRequest request, PostEntity Post) {
        Optional<UserEntity> userEntity = userRepository.findById(request.getUserId());
        Post.setText(request.getText());
        Post.setUser(userEntity.get());
        Post.setCreatedAt(request.getCreatedAt());
        postsRepository.save(Post);
    }
}
