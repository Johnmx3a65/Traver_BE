package com.parovsky.traver.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoView {
    private Long id;

    private String previewUrl;

    private String fullUrl;

    private Long locationId;
}
