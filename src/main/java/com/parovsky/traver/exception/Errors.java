package com.parovsky.traver.exception;

import org.springframework.http.HttpStatus;


public enum Errors implements ErrorResponse {
	LOCATION_NOT_FOUND( "LOCATION_NOT_FOUND", HttpStatus.NOT_FOUND , "Location with id ${id} not found"),
	LOCATION_ALREADY_EXIST( "LOCATION_ALREADY_EXIST", HttpStatus.CONFLICT , "Location with name ${name} and subtitle ${subtitle} already exist"),
	LOCATION_HAS_PHOTOS( "LOCATION_HAS_PHOTOS", HttpStatus.CONFLICT , "Location with id ${id} has photos"),
	FAVORITE_LOCATION_ALREADY_EXIST( "FAVORITE_LOCATION_ALREADY_EXIST", HttpStatus.CONFLICT , "User already has favorite location with id ${locationId}"),
	FAVORITE_LOCATION_NOT_FOUND( "FAVORITE_LOCATION_NOT_FOUND", HttpStatus.NOT_FOUND , "User has not favorite location with id ${locationId}"),
	PHOTO_NOT_FOUND( "PHOTO_NOT_FOUND", HttpStatus.NOT_FOUND , "Photo with id ${id} not found"),
	CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND , "Category with id ${id} not found"),
	CATEGORY_ALREADY_EXIST("CATEGORY_ALREADY_EXIST", HttpStatus.CONFLICT , "Category with name ${name} already exist"),
	CATEGORY_HAS_LOCATIONS("CATEGORY_HAS_LOCATIONS", HttpStatus.CONFLICT , "Category with id ${id} has locations"),
	USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND , "User with id ${id} not found"),
	USER_NOT_FOUND_BY_EMAIL("USER_NOT_FOUND", HttpStatus.NOT_FOUND , "User with email ${email} not found"),
	USER_ALREADY_EXIST("USER_ALREADY_EXIST", HttpStatus.CONFLICT , "User with email ${email} already exist"),
	VERIFICATION_CODE_NOT_MATCH("VERIFICATION_CODE_NOT_MATCH", HttpStatus.BAD_REQUEST , "Verification code doesn't match"),
	BAD_CREDENTIALS("BAD_CREDENTIALS", HttpStatus.BAD_REQUEST , "Bad Credentials"),
	UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "User is unauthorized"),
	SERVER_ERROR("SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Unexpected issue...");

	final String key;
	final HttpStatus httpStatus;
	final String message;

	Errors(String key, HttpStatus httpStatus, String message) {
		this.message = message;
		this.key = key;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
