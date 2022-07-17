package com.parovsky.traver.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @Column(name = "category_picture", nullable = false, columnDefinition="TEXT")
    private String picture;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Location> locations = new ArrayList<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
