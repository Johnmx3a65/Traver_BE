package com.parovsky.traver.utils;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.dto.UserResponse;

public class ModelMapper {

	private ModelMapper() {}

	public static UserResponse mapUserDTO(UserDTO userDTO) {
		return UserResponse.builder()
				.id(userDTO.getId())
				.email(userDTO.getMail())
				.name(userDTO.getName())
				.role(userDTO.getRole())
				.verifyCode(userDTO.getVerifyCode()).build();
	}
}
