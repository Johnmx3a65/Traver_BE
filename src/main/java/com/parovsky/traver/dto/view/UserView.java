package com.parovsky.traver.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserView {
	private Long id;

	private String name;

	private String email;

	private String role;

	private String verifyCode;
}
