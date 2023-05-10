package com.parovsky.traver.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
public class UpdateCategoryModel {
    private Long id;

    private String name;

    private String picture;
}
