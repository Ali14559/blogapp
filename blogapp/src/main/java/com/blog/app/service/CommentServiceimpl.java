package com.blog.app.service;

import com.blog.app.dto.CommentDto;
import com.blog.app.entity.Comment;
import com.blog.app.entity.Post;
import com.blog.app.exception.BlogApiException;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.repository.CommentRepository;
import com.blog.app.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceimpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
       Comment comment = modelMapper.map(commentDto, Comment.class);
       Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
       comment.setPost(post);
       Comment newComment = commentRepository.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
       List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map((comment)->modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Resource not Found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment Not found"));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belongs to the post");
        }
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belongs to the post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
       Comment comment1 = commentRepository.save(comment);

        return modelMapper.map(comment1, CommentDto.class);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment not found"));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belongs to the post");
        }
        commentRepository.delete(comment);

    }
}
