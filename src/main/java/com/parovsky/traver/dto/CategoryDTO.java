package com.parovsky.traver.dto;

import org.springframework.lang.NonNull;

public class CategoryDTO {
    private Long id;

    private String name;

    private String picture;

    public CategoryDTO(Long id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
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

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getPicture() {
    	return picture;
    }

    public void setPicture(String picture) {
    	this.picture = picture;
    }
}
