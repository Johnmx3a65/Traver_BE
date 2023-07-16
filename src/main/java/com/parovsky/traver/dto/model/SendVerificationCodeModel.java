package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class SendVerificationCodeModel {
	@Size(min = 1, max = 255, message = EMAIL_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;
}
