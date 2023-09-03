package com.parovsky.traver.dto.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Constraints.*;

@Data
public class SendVerificationCodeForm {
	@Size(min = 1, max = 255, message = EMAIL_LENGTH)
	@Email(message = EMAIL_PATTERN)
	@NotBlank(message = EMPTY_EMAIL)
	private String email;
}
