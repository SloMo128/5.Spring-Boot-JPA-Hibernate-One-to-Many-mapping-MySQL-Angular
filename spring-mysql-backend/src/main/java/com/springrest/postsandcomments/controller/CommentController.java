package com.springrest.postsandcomments.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springrest.postsandcomments.exception.ResourceNotFoundException;
import com.springrest.postsandcomments.entities.Comment;
import com.springrest.postsandcomments.entities.Post;
import com.springrest.postsandcomments.repo.CommentRepository;
import com.springrest.postsandcomments.repo.PostRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/spring-rest-api", produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200") // Angular Home Page

public class CommentController {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public CommentController(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    // Comments
    @GetMapping("/posts/comments/{postId}")
    public ResponseEntity<Object> getAllCommentsByPostId(@PathVariable("postId") Long postId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "100") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sort,
    		@RequestParam(name="order", required = false, defaultValue = "DESC") String order)throws NotFoundException {
        Pageable paging = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));
        Page<Comment> pageResult = commentRepo.findByPostId(postId, paging);
        Map<String, Object> responseBody = new LinkedHashMap<>();
        if (pageResult.hasContent()) {
            responseBody.put("comments", pageResult.getContent());
            responseBody.put("count", commentRepo.countByPostId(postId));
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No comments found for post with id: " + postId);
        }
    }
    
    @PostMapping("/posts/commentspost/{postId}")
    public ResponseEntity<Comment> addCommentToPost(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody Comment comment) {
        
        Optional<Post> postOptional = postRepo.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            comment.setPost(post); // Associa il commento al post
            Comment savedComment = commentRepo.save(comment); // Salva il commento
            
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } else {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody Comment commentDetails) {
    	
    	//System.out.println(commentDetails.getContent());
        
        // Verifica se il post esiste
        if (!postRepo.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        // Trova il commento esistente
        Optional<Comment> commentOptional = commentRepo.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            // Aggiorna i dettagli del commento
            
            comment.setBody(commentDetails.getBody());
            // Salva il commento aggiornato
            Comment updatedComment = commentRepo.save(comment);
            return new ResponseEntity<>(updatedComment, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
    }

    @DeleteMapping("/postsdelite/{postId}/commentsdelite/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId) {
        
        // Verifica se il post esiste
        if (!postRepo.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }

        try {
            // Elimina il commento dal repository
            commentRepo.deleteById(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successo con nessun contenuto
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
    }

    
}
