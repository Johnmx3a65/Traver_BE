package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Constraints.*;

@Data
public class SaveLocationModel {
	@Size(min = 1, max = 255, message = NAME_LENGTH)
	@NotBlank(message = EMPTY_NAME)
	private String name;

	@Size(min = 1, max = 255, message = SUBTITLE_LENGTH)
	@NotBlank(message = EMPTY_SUBTITLE)
	private String subtitle;

	@Size(min = 2, message = DESCRIPTION_MIN_LENGTH)
	@NotBlank(message = EMPTY_DESCRIPTION)
	private String description;

	//pattern two float numbers divided by semicolon
	@Pattern(regexp = "^(\\d+\\.\\d+);(\\d+\\.\\d+)$", message = COORDINATES_PATTERN)
	private String coordinates;

	//pattern url
	@Pattern(regexp = "^(http|https)://.*$", message = PICTURE_PATTERN)
	private String picture;

	@NotNull(message = EMPTY_CATEGORY_ID)
	private Long categoryId;
}
