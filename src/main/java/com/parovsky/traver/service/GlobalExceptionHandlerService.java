package com.parovsky.traver.service;

import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.UnprocessableEntityException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;

public interface GlobalExceptionHandlerService {

	ResponseEntity<String> handleException(EntityNotFoundException e);

	ResponseEntity<String> handleException(EntityAlreadyExistsException e);

	ResponseEntity<String> handleException(AuthenticationException e);

	ResponseEntity<String> handleException(VerificationCodeNotMatchException e);

	ResponseEntity<String> handleException(UnprocessableEntityException e);

	ResponseEntity<String> handleException(HttpMessageNotReadableException e);

	ResponseEntity<String> handleException(Throwable e);
}
