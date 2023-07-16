package com.parovsky.traver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
//todo remove this class
public class UnprocessableEntityException extends ResponseStatusException {
	public UnprocessableEntityException(String reason) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
	}
}
