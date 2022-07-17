package com.parovsky.traver.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "location_name", nullable = false)
    private String name;

    @Column(name = "location_subtitle", nullable = false)
    private String subtitle;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "coordinates", nullable = false)
    private String coordinates;

    @Column(name = "location_picture", nullable = false, columnDefinition="TEXT")
    private String picture;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<Photo> photos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() { return subtitle;}

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getPicture() { return picture;}

    public void setPicture(String picture) { this.picture = picture;}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
