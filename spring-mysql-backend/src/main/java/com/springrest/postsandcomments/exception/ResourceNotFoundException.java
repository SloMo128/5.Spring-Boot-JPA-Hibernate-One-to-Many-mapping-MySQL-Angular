package com.springrest.postsandcomments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
		super(message);
	}

	public ResourceNotFoundException(final Throwable cause) {
		super(cause);
	}

	public ResourceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
