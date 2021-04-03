package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreationTimeDesc();

    List<Post> findPostByTagsContains(Tag tag);
}
