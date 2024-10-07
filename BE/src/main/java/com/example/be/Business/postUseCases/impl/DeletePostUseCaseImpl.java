package com.example.be.Business.postUseCases.impl;

import com.example.be.Business.postUseCases.DeletePostUseCase;
import com.example.be.Repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeletePostUseCaseImpl implements DeletePostUseCase {
    private final PostsRepository postsRepository;

    @Override
    @Transactional
    public void deletePost(long PostId) {
        this.postsRepository.deleteById(PostId);
    }
}
