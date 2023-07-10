package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class UpdateLocationModel {

	@NonNull
	@NotNull(message = EMPTY_LOCATION_ID)
	private Long id;

	@NonNull
	@Max(value = 255, message = NAME_MAX_LENGTH)
	@NotBlank(message = EMPTY_NAME)
	private String name;

	@NonNull
	@Min(value = 2, message = SUBTITLE_MIN_LENGTH)
	@Max(value = 255, message = SUBTITLE_MAX_LENGTH)
	@NotBlank(message = EMPTY_SUBTITLE)
	private String subtitle;

	@NonNull
	@Min(value = 2, message = DESCRIPTION_MIN_LENGTH)
	@NotBlank(message = EMPTY_DESCRIPTION)
	private String description;

	@NonNull
	//pattern two float numbers divided by semicolon
	@Pattern(regexp = "^(\\d+\\.\\d+);(\\d+\\.\\d+)$", message = COORDINATES_PATTERN)
	private String coordinates;

	@NonNull
	//pattern url
	@Pattern(regexp = "^(http|https)://.*$", message = URL_PATTERN)
	private String picture;

	@NonNull
	@NotNull(message = EMPTY_CATEGORY_ID)
	private Long categoryId;
}
