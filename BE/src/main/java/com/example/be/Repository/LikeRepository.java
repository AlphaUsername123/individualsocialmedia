package com.example.be.Repository;

import com.example.be.Repository.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Long countByPostId(Long postId);
}
