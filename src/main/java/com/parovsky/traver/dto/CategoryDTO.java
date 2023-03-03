package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    private String name;

    private String picture;
}
