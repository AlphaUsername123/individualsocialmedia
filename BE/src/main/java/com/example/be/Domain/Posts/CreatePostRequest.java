    package com.example.be.Domain.Posts;

    import com.example.be.Repository.entity.UserEntity;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class CreatePostRequest {
        @NotBlank
        private String text;
        @NotNull
        private Long userId;
        @NotNull
        private LocalDateTime createdAt;
    }
