package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class SendVerificationCodeModel {
	@NonNull
	@Max(value = 255, message = EMAIL_MAX_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;
}
