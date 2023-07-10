package com.parovsky.traver.dto.model;

import com.parovsky.traver.role.Role;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class UpdateUserModel {
	@NonNull
	@NotNull(message = EMPTY_USER_ID)
	private Long id;

	@NonNull
	@Max(value = 255, message = NAME_MAX_LENGTH)
	@NotBlank(message = EMPTY_NAME)
	private String name;

	@NonNull
	@Max(value = 255, message = EMAIL_MAX_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;

	@NonNull
	@Pattern(regexp = "^(USER|ADMIN)$", message = ROLE_PATTERN)
	private Role role;
}
