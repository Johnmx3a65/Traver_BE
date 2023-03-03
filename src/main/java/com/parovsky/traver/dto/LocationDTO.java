package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    private String name;

    private String subtitle;

    private String description;

    private String coordinates;

    private String picture;

    private Long categoryId;

    public LocationDTO(Long id, String name, String description, String coordinates, Long categoryId, String picture, String subtitle) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
        this.categoryId = categoryId;
        this.picture = picture;
        this.subtitle = subtitle;
    }
}
