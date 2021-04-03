package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Post find(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Post> findByTag(Tag tag) {
        return postRepository.findPostByTagsContains(tag);
    }
}
