package com.parovsky.traver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private Long id;

    private String name;

    private String subtitle;

    private String description;

    private String coordinates;

    private String picture;

    private Long categoryId;

    private Boolean isFavorite;
}
