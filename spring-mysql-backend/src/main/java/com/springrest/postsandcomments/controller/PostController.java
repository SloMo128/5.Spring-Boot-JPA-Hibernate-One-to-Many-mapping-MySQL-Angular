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

public class PostController {

    //private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public PostController(PostRepository postRepo) {
       
        this.postRepo = postRepo;
    }

    @GetMapping("/posts")
	public ResponseEntity<Object> getCustomers(
	        @RequestParam(name="page", required = false, defaultValue = "0") int page,
	        @RequestParam(name="size", required = false, defaultValue = "10") int size,
	        @RequestParam(name="sort",required = false, defaultValue = "id") String sort,
	        @RequestParam(name="order", required = false, defaultValue = "DESC") String order) {
	    Map<String, Object> responseBody = new LinkedHashMap<>();
	    Sort.Direction sortDirection = "DESC".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
	    Pageable paging = PageRequest.of(page, size, Sort.by(sortDirection, sort));
	    Page<Post> pagedResult = postRepo.findAll(paging);
	    
	    if (pagedResult.hasContent()) {
	        responseBody.put("posts", pagedResult.getContent());
	        responseBody.put("postsCount", postRepo.count());
	        return new ResponseEntity<>(responseBody, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	    }
	}
    
    // Comments
    @GetMapping("/post/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable("id") Long id) {
		Optional<Post> optPost = postRepo.findById(id);
		if (optPost.isPresent()) {
			return new ResponseEntity<>(optPost.get(), HttpStatus.OK);
		}
		//return new ResponseEntity<>(optUser.get(), HttpStatus.NOT_FOUND);
		throw new ResourceNotFoundException("Post not found with id: " + id);
	
    }
    
    @PostMapping(path = "/post", consumes = "application/json")
	public ResponseEntity<Post> postPost(@Valid @RequestBody Post post) {
		try {
			postRepo.save(post);
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		} catch (Exception e) {
			//return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			throw new RuntimeException("An error occurred while saving the post: " + e.getMessage());
		}
	}
    
    @PutMapping(path = "/post/put/{id}", consumes = "application/json")
	public ResponseEntity<Post> putPost(@PathVariable("id") Long id, @Valid @RequestBody Post post) {
		try {
			Optional<Post> optionalPost = postRepo.findById(id);
			if (optionalPost.isPresent()) {
				Post existingPost = optionalPost.get();
				existingPost.setDescription(post.getDescription());
				existingPost.setTitle(post.getTitle());
				existingPost.setContent(post.getContent());

			    postRepo.save(existingPost);
				return new ResponseEntity<>(null, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			//return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			throw new RuntimeException("An error occurred while updating the post: " + e.getMessage());
		}
	}
    
    @DeleteMapping(path = "/post/delete/{id}")
	public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
		try {
			postRepo.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Post not found with id: " + id);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while deleting the Post: " + e.getMessage());
		}
	}
}
