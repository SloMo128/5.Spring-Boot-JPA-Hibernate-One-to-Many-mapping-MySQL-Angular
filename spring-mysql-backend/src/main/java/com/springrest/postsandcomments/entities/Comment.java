package com.springrest.postsandcomments.entities;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "spring_comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Body can't be empty")
	@Column(columnDefinition="LONGTEXT")
	//@Lob
	private String body;

	//@NotBlank(message = "username can't be empty")
	@Size(max = 25, message = "username can be max 25 characters long")
	private String username;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private Date createdAt;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Post post;

	public Comment() {
		this.body = "";
		this.username = "";
	};

	public Comment(String body, String username) {
		this.body = body;
		this.username = username;
	};
	
	@Override
	public String toString() {
		return "Comment [id=" + this.getId() + ", " 
				+ "body=" + this.getBody() + ", " 
				+ "username=" + this.getUsername() + ", " 
				+ "post_id=" + this.getPost().getId() + ", " 
				+ "createdAt=" + this.getCreatedAt() + "]";
	}
}
