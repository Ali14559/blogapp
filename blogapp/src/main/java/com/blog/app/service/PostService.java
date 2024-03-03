package com.blog.app.service;

import com.blog.app.dto.PostDto;
import com.blog.app.dto.PostResponse;
import com.blog.app.entity.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto getPost(Long id);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto updatePost(Long id, PostDto postDto);
    void  deletePost(Long id);
    List<PostDto> getPostsByCategory(Long categoryId);
    List<PostDto> searchPosts(String title);
}
