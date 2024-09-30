package com.example.be.Controller;

import com.example.be.Business.postUseCases.*;
import com.example.be.Domain.Posts.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping(value = "/Posts")
public class PostsController {
    private final CreatePostUseCase createPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final GetPostsUseCase getPostsUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @GetMapping("/getall")
    public ResponseEntity<GetAllPostsResponse> getAll() {
        GetAllPostsRequest request = new GetAllPostsRequest();
        return ResponseEntity.ok(getPostsUseCase.getPosts(request));
    }


    @GetMapping("{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") int id) {
        final Optional<Post> PostOptional = getPostUseCase.getPost(id);
        if (PostOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(PostOptional.get());
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping()
    public ResponseEntity<CreatePostResponse> create(@RequestBody @Valid CreatePostRequest request) {
            CreatePostResponse response = createPostUseCase.createPost(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RolesAllowed({"ADMIN"})
    @PutMapping("{id}")
    public ResponseEntity<Post> update(@PathVariable("id") long id, @RequestBody @Valid UpdatePostRequest request) {
        request.setId(id);
        updatePostUseCase.updatePost(request);
        return ResponseEntity.noContent().build();
    }

    @RolesAllowed({"ADMIN"})
    @DeleteMapping("{PostId}")
    public ResponseEntity<Void> deletePost(@PathVariable int PostId) {
        deletePostUseCase.deletePost(PostId);
        return ResponseEntity.noContent().build();
    }
}