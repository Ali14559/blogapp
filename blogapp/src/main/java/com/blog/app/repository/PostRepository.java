package com.blog.app.repository;

import com.blog.app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
    @Query( "SELECT p FROM Post p  WHERE p.title LIKE :key")
    List<Post> searchPosts(@Param("key") String title);
}
