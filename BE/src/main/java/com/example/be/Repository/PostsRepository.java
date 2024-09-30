package com.example.be.Repository;

import com.example.be.Repository.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<PostEntity, Long> {
}