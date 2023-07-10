package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

@Data
public class SignUpModel {
	@NonNull
	@Min(value = 2, message = "Name should be at least 2 characters")
	@Max(value = 255, message = "Name should be less than 255 characters")
	@NotBlank(message = "Name cannot be empty")
	private String name;

	@NonNull
	@Max(value = 255, message = "Email should be less than 255 characters")
	@Email(message = "Email should be valid")
	@NotBlank(message ="Email cannot be empty")
	private String email;

	@NonNull
	@Pattern(regexp = "^[a-zA-Z0-9_]{6,255}$", message = "Password should be 6-255 characters, only letters, numbers and underscore")
	@NotBlank(message = "Password cannot be empty")
	private String password;
}
