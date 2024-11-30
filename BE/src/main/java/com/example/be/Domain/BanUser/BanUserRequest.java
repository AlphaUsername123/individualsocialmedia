package com.example.be.Domain.BanUser;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BanUserRequest {
    private Long userId;
    private String reason;
    private LocalDateTime banUntil;
}
