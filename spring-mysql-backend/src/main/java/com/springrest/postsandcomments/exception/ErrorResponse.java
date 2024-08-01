package com.springrest.postsandcomments.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
	private final Date timestamp;
	private final String status;
	private final String message;
	private List<ErrorItem> errors;

	@Getter
	@Setter
	@RequiredArgsConstructor
	private static class ErrorItem {
		private final String field;
		private final String message;
	}

	public void addErrorItem(String field, String message) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<>();
		}
		errors.add(new ErrorItem(field, message));
	}

}