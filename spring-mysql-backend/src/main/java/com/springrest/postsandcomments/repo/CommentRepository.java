package com.springrest.postsandcomments.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.springrest.postsandcomments.entities.Comment;
import com.springrest.postsandcomments.entities.Post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>,PagingAndSortingRepository<Comment, Long> 
{

	void deleteAll();
	
	List<Comment> findAll();

	Page<Comment> findByPostId(Long postId, Pageable paging);

	Object countByPostId(Long postId); 

}