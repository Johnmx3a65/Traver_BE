package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordModel {
	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@Size(min = 4, max = 4, message = "verifyCode should have 4 digits")
	@NotBlank
	private String verifyCode;
}
