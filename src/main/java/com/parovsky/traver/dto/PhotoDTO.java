package com.parovsky.traver.dto;

public class PhotoDTO {
    private Long id;

    private String photo;

    private Long locationId;

    public PhotoDTO(Long id, String photo, Long locationId) {
        this.id = id;
        this.photo = photo;
        this.locationId = locationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
