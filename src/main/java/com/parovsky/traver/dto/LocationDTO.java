package com.parovsky.traver.dto;

import org.springframework.lang.NonNull;

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
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NonNull String subtitle) {
        this.subtitle = subtitle;
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
    public String getPicture() { return picture; }

    public void setPicture(@NonNull String picture) { this.picture = picture;}

    @NonNull
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Long categoryId) {
        this.categoryId = categoryId;
    }

}
