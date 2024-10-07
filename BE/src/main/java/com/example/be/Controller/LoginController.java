package com.example.be.Controller;

import com.example.be.Business.loginUseCases.LoginUseCase;
import com.example.be.Domain.LoginRequest;
import com.example.be.Domain.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class LoginController
{
    private final LoginUseCase loginUseCase;

    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
