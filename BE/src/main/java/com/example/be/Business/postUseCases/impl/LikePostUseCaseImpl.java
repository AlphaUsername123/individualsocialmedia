package com.example.be.Business.postUseCases.impl;

import com.example.be.Business.postUseCases.LikePostUseCase;
import com.example.be.Repository.LikeRepository;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.UserRepository;
import com.example.be.Repository.entity.LikeEntity;
import com.example.be.Repository.entity.PostEntity;
import com.example.be.Repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikePostUseCaseImpl implements LikePostUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public Long likePost(Long userId, Long postId) {
        // Fetch user and post to ensure they exist
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        PostEntity post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Create a new Like entity
        LikeEntity like = new LikeEntity();
        like.setUser(user);
        like.setPost(post);

        // Save the like to the repository
        likeRepository.save(like);

        // Return the updated like count for the post
        return likeRepository.countByPostId(postId);
    }

}
