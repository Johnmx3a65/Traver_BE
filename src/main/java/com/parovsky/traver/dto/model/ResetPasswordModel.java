package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Constraints.*;

@Data
public class ResetPasswordModel {
	@Size(min = 1, max = 255, message = EMAIL_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;

	@Pattern(regexp = "^[0-9]{4}$", message = VERIFICATION_CODE_PATTERN)
	private String verificationCode;

	@Pattern(regexp = "^[a-zA-Z0-9_]{6,255}$", message = PASSWORD_PATTERN)
	@NotBlank(message = EMPTY_PASSWORD)
	private String password;
}
