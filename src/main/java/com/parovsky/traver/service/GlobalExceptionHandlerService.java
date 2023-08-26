package com.parovsky.traver.service;

import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

public interface GlobalExceptionHandlerService {

	ResponseEntity<String> handleException(EntityNotFoundException e);

	ResponseEntity<String> handleException(EntityAlreadyExistsException e);

	ResponseEntity<String> handleException(AuthenticationException e);

	ResponseEntity<String> handleException(VerificationCodeNotMatchException e);

	ResponseEntity<String> handleException(HttpMessageNotReadableException e);

	ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e);

	ResponseEntity<String> handleException(BadCredentialsException e);

	ResponseEntity<String> handleException(Throwable e);
}
