package com.parovsky.traver.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
public class CategoryModel {
    private Long id;

    private String name;

    private String picture;

    @Nullable
    public Long getId() {
        return id;
    }
}
