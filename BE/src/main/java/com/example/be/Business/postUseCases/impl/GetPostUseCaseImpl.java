package com.example.be.Business.postUseCases.impl;

import com.example.be.Business.postUseCases.GetPostUseCase;
import com.example.be.Domain.Posts.Post;
import com.example.be.Repository.PostsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetPostUseCaseImpl implements GetPostUseCase {
    private PostsRepository postsRepository;

    @Override
    public Optional<Post> getPost(long PostId) {
        return postsRepository.findById(PostId).map(PostConverter::convert);
    }
}
