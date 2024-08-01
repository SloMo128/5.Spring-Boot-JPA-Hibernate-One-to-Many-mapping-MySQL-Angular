package com.springrest.postsandcomments.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.springrest.postsandcomments.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>,PagingAndSortingRepository<Post, Long> {



}
