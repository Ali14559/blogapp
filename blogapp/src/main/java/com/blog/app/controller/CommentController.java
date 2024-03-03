package com.blog.app.controller;

import com.blog.app.dto.CommentDto;
import com.blog.app.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "REST APIs for Comment Resource"
)
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/post/{postId}/addcomment")
    public ResponseEntity<CommentDto>createComment(@PathVariable long postId,@Valid @RequestBody CommentDto commentDto){
       return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Comments REST API",
            description = "Get All Comments REST API is used to fetch all the comments from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/post/{postId}/getcomments")
    public ResponseEntity<List<CommentDto>>getCommentsByPostId(@PathVariable long postId){
       return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);

    }

    @Operation(
            summary = "Get Comment By Id REST API",
            description = "Get Comment By Id REST API is used to get single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/post/{postId}/getcomment/{id}")
    public ResponseEntity<CommentDto>getCommentById(@PathVariable Long postId,
                                                    @PathVariable("id") Long CommentId){
      return  new ResponseEntity<>(commentService.getCommentById(postId, CommentId), HttpStatus.OK);

    }

    @Operation(
            summary = "update Comment REST API",
            description = "Update Comment REST API is used to update a particular comment in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PutMapping("/post/{postId}/updatecomment/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable Long postId, @PathVariable("id") long commentId,@Valid @RequestBody CommentDto commentDto){
       return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API is used to delete a particular comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @DeleteMapping("/post/{postId}/deletecomment/{id}")
    public ResponseEntity<String>deleteComment(@PathVariable Long postId,@PathVariable("id") Long commentId){
        commentService.deleteComment(postId,commentId);
        return ResponseEntity.ok("Comment Deletd Successfully");
    }

}