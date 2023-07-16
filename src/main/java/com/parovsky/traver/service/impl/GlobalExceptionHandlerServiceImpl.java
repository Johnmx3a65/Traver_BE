package com.parovsky.traver.service.impl;

import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.UnprocessableEntityException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import com.parovsky.traver.service.GlobalExceptionHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GlobalExceptionHandlerServiceImpl implements GlobalExceptionHandlerService {

	@Override
	public ResponseEntity<String> handleException(EntityNotFoundException e) {
		log.error("Entity not found");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<String> handleException(EntityAlreadyExistsException e) {
		log.error("Entity already exists");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<String> handleException(AuthenticationException e) {
		log.error("User is unauthorised");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<String> handleException(VerificationCodeNotMatchException e) {
		log.error("Verification code doesn't match");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<String> handleException(UnprocessableEntityException e) {
		log.error("Unprocessable entity.");
		return new ResponseEntity<>(e.getReason(), e.getStatus());
	}

	@Override
	public ResponseEntity<String> handleException(HttpMessageNotReadableException e) {
		log.error("Parsing error");
		return new ResponseEntity<>("Error parsing request body.", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<String> handleException(Throwable e) {
		log.error(e.getMessage());
		return new ResponseEntity<>("Something went wrong. Unexpected issue...", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
