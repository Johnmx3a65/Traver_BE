package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class UpdateCategoryModel {
    @NonNull
    @NotNull(message = EMPTY_CATEGORY_ID)
    private Long id;

    @NonNull
    @Max(value = 255, message = NAME_MAX_LENGTH)
    @NotBlank(message = EMPTY_NAME)
    private String name;

    @NonNull
    //pattern url
    @Pattern(regexp = "^(http|https)://.*$", message = PICTURE_PATTERN)
    @NotBlank(message = EMPTY_PICTURE)
    private String picture;
}
