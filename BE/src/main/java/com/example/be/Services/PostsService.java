package com.example.be.Services;

import com.example.be.Domain.Post;
import com.example.be.Repository.PostsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostsService {

    @Autowired
    @Qualifier("postsRepository")
    private PostsRepository repo;

    public List<Post> getAll() {
        return repo.findAll();
    }

    public void create(Post post) {
        this.repo.save(post);
    }

    public void delete(Post post) {
       this.repo.delete(post);
    }

    public Post getById(int id) {
            Long longId = Long.valueOf(id);
        if (longId != null) {
            return repo.findById(longId).get();
        }
        return null;
    }
}
