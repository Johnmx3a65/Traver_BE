package com.parovsky.traver.utils;

public class Contstrains {
	public static final String EMAIL_MAX_LENGTH = "Email should be less than 255 characters";
	public static final String EMPTY_EMAIL = "Email cannot be empty";
	public static final String EMAIL_PATTERN = "Email should be valid";

	public static final String EMPTY_PASSWORD = "Password cannot be empty";
	public static final String PASSWORD_PATTERN = "Password should be 6-255 characters, only letters, numbers and underscore";

	public static final String NAME_MAX_LENGTH = "Name should be less than 255 characters";
	public static final String EMPTY_NAME = "Name cannot be empty";

	public static final String SUBTITLE_MIN_LENGTH = "Subtitle should be at least 2 characters";
	public static final String SUBTITLE_MAX_LENGTH = "Subtitle should be less than 255 characters";
	public static final String EMPTY_SUBTITLE = "Subtitle cannot be empty";

	public static final String DESCRIPTION_MIN_LENGTH = "Description should be at least 2 characters";
	public static final String EMPTY_DESCRIPTION = "Description cannot be empty";

	public static final String EMPTY_COORDINATES = "Coordinates cannot be empty";
	public static final String COORDINATES_PATTERN = "Coordinates should be two float numbers divided by semicolon";

	public static final String EMPTY_PICTURE = "Picture cannot be empty";
	public static final String URL_PATTERN = "Picture should be url";

	public static final String VERIFICATION_CODE_PATTERN = "Verification code should be 4 digits";
	public static final String ROLE_PATTERN = "Role should be USER or ADMIN";

	public static final String EMPTY_LOCATION_ID = "Location id cannot be empty";
	public static final String EMPTY_CATEGORY_ID = "Category id cannot be empty";
	public static final String EMPTY_USER_ID = "User id cannot be empty";
}
