package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.parovsky.traver.utils.Constraints.*;

@Data
public class SaveCategoryModel {
	@Size(min = 1, max = 255, message = NAME_LENGTH)
	@NotBlank(message = EMPTY_NAME)
	private String name;

	//pattern url
	@Pattern(regexp = "^(http|https)://.*$", message = PICTURE_PATTERN)
	@NotBlank(message = EMPTY_PICTURE)
	private String picture;
}
