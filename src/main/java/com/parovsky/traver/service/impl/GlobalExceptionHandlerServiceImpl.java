package com.parovsky.traver.service.impl;

import com.parovsky.traver.exception.EntityIsAlreadyExistException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import com.parovsky.traver.service.GlobalExceptionHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GlobalExceptionHandlerServiceImpl implements GlobalExceptionHandlerService {

	@Override
	public ResponseEntity<String> handleExceptions(Throwable e) {
		if (e instanceof EntityNotFoundException) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} else if (e instanceof VerificationCodeNotMatchException) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} else if (e instanceof EntityIsAlreadyExistException) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} else if (e instanceof AuthenticationException) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		log.error(e.getMessage());
		return new ResponseEntity<>("Something went wrong. Unexpected issue...", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
