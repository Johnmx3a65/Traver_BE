package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class SignInModel {
	@NonNull
	@Max(value = 255, message = EMAIL_MAX_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;

	@NonNull
	@Pattern(regexp = "^[a-zA-Z0-9_]{6,255}$", message = PASSWORD_PATTERN)
	@NotBlank(message = EMPTY_PASSWORD)
	private String password;
}
