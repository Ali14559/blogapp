package com.blog.app.service;

import com.blog.app.dto.PostDto;
import com.blog.app.dto.PostResponse;
import com.blog.app.entity.Category;
import com.blog.app.entity.Post;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.repository.CategoryRepository;
import com.blog.app.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceimpl implements PostService{

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
       Post post = modelMapper.map(postDto, Post.class);
      Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource Not Found"));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        // create pagiable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts= postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
       Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource Not Found"));
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
      Post post1= postRepository.save(post);
        return modelMapper.map(post1, PostDto.class);
    }

    @Override
    public void deletePost(Long id) {
      Post post =  postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resourse not found"));
      postRepository.deleteById(id);

    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String title) {
       List<Post> posts = postRepository.searchPosts("%"+title+"%");
        return posts.stream().map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
