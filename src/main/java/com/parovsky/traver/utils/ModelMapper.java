package com.parovsky.traver.utils;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.PhotoResponse;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.dto.UserResponse;

public class ModelMapper {

	private ModelMapper() {}

	public static UserResponse mapUserDTO(UserDTO userDTO) {
		return UserResponse.builder()
				.id(userDTO.getId())
				.email(userDTO.getEmail())
				.name(userDTO.getName())
				.role(userDTO.getRole())
				.verifyCode(userDTO.getVerifyCode()).build();
	}

	public static PhotoResponse mapPhotoDTO(PhotoDTO photoDTO) {
		return PhotoResponse.builder()
				.id(photoDTO.getId())
				.url(photoDTO.getUrl())
				.locationId(photoDTO.getLocationId())
				.build();
	}
}
