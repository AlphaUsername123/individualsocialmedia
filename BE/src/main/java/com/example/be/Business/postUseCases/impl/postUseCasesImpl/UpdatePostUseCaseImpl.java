package com.example.be.Business.postUseCases.impl.postUseCasesImpl;

import com.example.be.Business.exception.PostAlreadyExistsException;
import com.example.be.Business.postUseCases.UpdatePostUseCase;
import com.example.be.Domain.Posts.UpdatePostRequest;
import com.example.be.Repository.PostsRepository;
import com.example.be.Repository.entity.PostEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePostUseCaseImpl implements UpdatePostUseCase {
    private final PostsRepository postsRepository;

    @Transactional
    @Override
    public void updatePost(UpdatePostRequest request) {
        Optional<PostEntity> PostOptional = postsRepository.findById(request.getId());
        if (PostOptional.isEmpty()) {
            throw new PostAlreadyExistsException("Post_ID_INVALID");
        }

        PostEntity Post = PostOptional.get();
        updateFields(request, Post);
    }

    private void updateFields(UpdatePostRequest request, PostEntity Post) {
        Post.setText(request.getText());
        postsRepository.save(Post);
    }
}
