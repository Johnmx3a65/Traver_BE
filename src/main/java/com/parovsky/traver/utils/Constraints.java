package com.parovsky.traver.utils;

public class Constraints {

	private Constraints() {
		throw new IllegalStateException("Utility class");
	}

	public static final String EMAIL_LENGTH = "Email should be between 1 and 255 characters";
	public static final String EMPTY_EMAIL = "Email cannot be empty";
	public static final String EMAIL_PATTERN = "Email should be valid";

	public static final String EMPTY_PASSWORD = "Password cannot be empty";
	public static final String PASSWORD_PATTERN = "Password should be 6-255 characters, only letters, numbers and underscore";

	public static final String NAME_LENGTH = "Name should be between 1 and 255 characters";
	public static final String EMPTY_NAME = "Name cannot be empty";

	public static final String SUBTITLE_LENGTH = "Subtitle should be between 2 and 255 characters";
	public static final String EMPTY_SUBTITLE = "Subtitle cannot be empty";

	public static final String DESCRIPTION_MIN_LENGTH = "Description should be at least 2 characters";
	public static final String EMPTY_DESCRIPTION = "Description cannot be empty";

	public static final String EMPTY_COORDINATES = "Coordinates cannot be empty";
	public static final String COORDINATES_PATTERN = "Coordinates should be two float numbers divided by semicolon (exmpl: 12.345;12.345)";

	public static final String EMPTY_PICTURE = "Picture cannot be empty";
	public static final String PICTURE_PATTERN = "Picture should be url";

	public static final String EMPTY_PREVIEW_URL = "Preview photo cannot be empty";
	public static final String PREVIEW_PHOTO_PATTERN = "Preview photo should be url";

	public static final String EMPTY_FULL_URL = "Full photo cannot be empty";
	public static final String FULL_PHOTO_PATTERN = "Full photo should be url";

	public static final String VERIFICATION_CODE_PATTERN = "Verification code should be 4 digits";
	public static final String ROLE_PATTERN = "Role should be USER or ADMIN";

	public static final String EMPTY_LOCATION_ID = "Location id cannot be empty";
	public static final String EMPTY_CATEGORY_ID = "Category id cannot be empty";
	public static final String EMPTY_USER_ID = "User id cannot be empty";
	public static final String EMPTY_PHOTO_ID = "Photo id cannot be empty";

	public static final String STATUS = "status";
	public static final String MESSAGE = "message";
	public static final String PATH = "path";
	public static final String ERROR = "error";
	public static final String ERRORS = "errors";
	public static final String ERROR_KEY = "errorKey";

	public static final String ID = "id";
	public static final String LOCATION_ID = "locationId";
	public static final String EMAIL = "email";
	public static final String NAME = "name";

}
