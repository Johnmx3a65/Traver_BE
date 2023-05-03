package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhotoDTO {
    private Long id;

    private String url;

    private Long locationId;
}
