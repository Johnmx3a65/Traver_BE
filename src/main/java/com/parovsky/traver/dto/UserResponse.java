package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	private Long id;

	private String name;

	private String email;

	private String role;

	private String verifyCode;
}
