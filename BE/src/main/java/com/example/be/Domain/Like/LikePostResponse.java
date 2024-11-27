package com.example.be.Domain.Like;

public class LikePostResponse {
    private Long likeCount;

    public LikePostResponse(Long likeCount) {
        this.likeCount = likeCount;
    }

    // Getters and Setters
    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }
}
