package com.example.be.Controller;

import com.example.be.Business.banUseCases.BanUserUseCase;
import com.example.be.Business.banUseCases.UnbanUserUseCase;
import com.example.be.Business.userUseCases.UpdateUserUseCase;
import com.example.be.Domain.BanUser.BanUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/MODERATOR")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MODERATOR')")
public class UserBanController {
    private final BanUserUseCase banUserUseCase;
    private final UnbanUserUseCase unbanUserUseCase;

    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestBody BanUserRequest request) {
        banUserUseCase.execute(request);
        return ResponseEntity.ok("User banned successfully.");
    }

    @DeleteMapping("/unban/{userId}")
    public ResponseEntity<String> unbanUser(@PathVariable Long userId) {
        unbanUserUseCase.execute(userId);
        return ResponseEntity.ok("User unbanned successfully.");
    }
}
