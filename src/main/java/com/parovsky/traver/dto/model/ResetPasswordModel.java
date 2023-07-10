package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordModel {
	@NonNull
	@Max(value = 255, message = "Email should be less than 255 characters")
	@Email(message = "Email should be valid")
	@NotBlank(message ="Email cannot be empty")
	private String email;

	@NonNull
	@Pattern(regexp = "^[0-9]{4}$", message = "Verification code must be 4 digits")
	private String verificationCode;

	@NonNull
	@Pattern(regexp = "^[a-zA-Z0-9_]{6,255}$", message = "Password should be 6-255 characters, only letters, numbers and underscore")
	@NotBlank(message = "Password cannot be empty")
	private String password;
}
