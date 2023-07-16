package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.*;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class UpdateUserModel {
	@NotNull(message = EMPTY_USER_ID)
	private Long id;

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
