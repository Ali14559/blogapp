package com.blog.app.controller;

import com.blog.app.dto.PostDto;
import com.blog.app.dto.PostResponse;
import com.blog.app.entity.Post;
import com.blog.app.service.PostService;
import com.blog.app.utills.AppConstans;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
@Tag(
        name = "REST APIs for Post Resource"
)
public class PostController {
    private PostService postService;
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/addpost")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new  ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    @GetMapping("/getpost/{id}")
    public ResponseEntity<PostDto>getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }
    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    @GetMapping("/getposts")
    public ResponseEntity<PostResponse>getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstans.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstans.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstans.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstans.DEFAULT_SORT_DIRECTION, required = false)String sortDir
    ){
        PostResponse postResponse =  postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
      return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }


    @Operation(
            summary = "update Post REST API",
            description = "Update Post REST API is used to update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/updatepost/{id}")
    public ResponseEntity<PostDto>updatePost(@PathVariable Long id,@Valid @RequestBody PostDto postDto){
       PostDto postDto1 =  postService.updatePost(id, postDto);
       return  new ResponseEntity<>(postDto1, HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<String>deletePost(@PathVariable Long id){
       postService.deletePost(id);
       return new  ResponseEntity<>("Post deleted Successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post By Category Id REST API",
            description = "Get Post By Category Id REST API is used to get all posts belong to a category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }

    @Operation(
            summary = "Search Posts REST API",
            description = "Search Posts REST API is used to search all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    @GetMapping("/posts/search/{title}")
    public ResponseEntity<List<PostDto>>searchPosts(
            @PathVariable("title") String title
    ){
        List<PostDto> postDtos = postService.searchPosts(title);
        return ResponseEntity.ok(postDtos);
    }
}
