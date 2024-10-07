package com.example.be.Business.postUseCases.impl;

import com.example.be.Domain.Posts.Post;
import com.example.be.Repository.entity.PostEntity;

public class PostConverter {
    private PostConverter() {
    }

    public static Post convert(PostEntity post) {
        return Post.builder()
                .id(post.getId())
                .text(post.getText())
                .build();
    }
}
