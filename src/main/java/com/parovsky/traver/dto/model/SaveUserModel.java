package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class SaveUserModel {
	@Size(min = 1, max = 255, message = NAME_LENGTH)
	@NotBlank(message = EMPTY_NAME)
	private String name;

	@Size(min = 1, max = 255, message = EMAIL_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;

	@Pattern(regexp = "^(USER|ADMIN)$", message = ROLE_PATTERN)
	private String role;
}
