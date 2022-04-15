package com.parovsky.traver.dto;

import org.springframework.lang.NonNull;

public class LocationDTO {

    private Long id;

    private String name;

    private String description;

    private String coordinates;

    private Long categoryId;

    public LocationDTO(Long id, String name, String description, String coordinates, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
        this.categoryId = categoryId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(@NonNull String coordinates) {
        this.coordinates = coordinates;
    }

    @NonNull
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Long categoryId) {
        this.categoryId = categoryId;
    }
}
