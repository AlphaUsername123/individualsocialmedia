package com.example.be.Controller;

import com.example.be.Domain.Post;
import com.example.be.Services.PostsService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:5173")
@RequestMapping(value = "/posts")
public class PostsController {
    private PostsService pservice;

    @GetMapping("/get")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(this.pservice.getAll());
    }


    @GetMapping("{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(this.pservice.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @RolesAllowed({"ADMIN"})
    @PostMapping("/post")
    public ResponseEntity create(@RequestBody Post newProject) {
        try {
            this.pservice.create(newProject);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @RolesAllowed({"ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") int id, @RequestBody Post post) {
        try {
            Post existingPost = this.pservice.getById(id);
            existingPost.setText(post.getText());
            this.pservice.create(existingPost);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            Post findpost = this.pservice.getById(id);
            this.pservice.delete(findpost);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}