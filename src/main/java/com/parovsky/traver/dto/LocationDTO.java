package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    private String name;

    private String subtitle;

    private String description;

    private String coordinates;

    private String picture;

    private Long categoryId;
}
