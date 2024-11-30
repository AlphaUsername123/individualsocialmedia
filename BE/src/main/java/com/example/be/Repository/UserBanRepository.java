package com.example.be.Repository;

import com.example.be.Repository.entity.UserBanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBanRepository extends JpaRepository<UserBanEntity, Long> {
    Optional<UserBanEntity> findByUserId(Long userId);
}
