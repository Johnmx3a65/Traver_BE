package com.parovsky.traver.dto.model;

import com.parovsky.traver.role.Role;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

@Data
public class UpdateUserModel {
	@NonNull
	@NotNull(message = "Id cannot be null")
	private Long id;

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
	@Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be USER or ADMIN")
	private Role role;
}
