package com.parovsky.traver.dto.model;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.parovsky.traver.utils.Contstrains.*;

@Data
public class UpdatePhotoModel {
    @NonNull
    @NotNull(message = EMPTY_PHOTO_ID)
    private Long id;

    @NonNull
    //pattern url
    @Pattern(regexp = "^(http|https)://.*$", message = PREVIEW_PHOTO_PATTERN)
    @NotBlank(message = EMPTY_PREVIEW_URL)
    private String previewUrl;

    @NonNull
    //pattern url
    @Pattern(regexp = "^(http|https)://.*$", message = FULL_PHOTO_PATTERN)
    @NotBlank(message = EMPTY_FULL_URL)
    private String fullUrl;

    @NonNull
    @NotNull(message = EMPTY_LOCATION_ID)
    private Long locationId;
}
