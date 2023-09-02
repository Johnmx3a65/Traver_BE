package com.parovsky.traver.dto.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.parovsky.traver.utils.Constraints.*;

@Data
public class SavePhotoModel {
    //pattern url
    @Pattern(regexp = "^(http|https)://.*$", message = PREVIEW_PHOTO_PATTERN)
    @NotBlank(message = EMPTY_PREVIEW_URL)
    private String previewUrl;

    //pattern url
    @Pattern(regexp = "^(http|https)://.*$", message = FULL_PHOTO_PATTERN)
    @NotBlank(message = EMPTY_FULL_URL)
    private String fullUrl;

    @NotNull(message = EMPTY_LOCATION_ID)
    private Long locationId;
}
