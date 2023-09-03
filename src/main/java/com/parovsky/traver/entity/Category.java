package com.parovsky.traver.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition="TEXT")
    private String picture;


    @JsonManagedReference
    @OneToMany(mappedBy = "category", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Location> locations = new ArrayList<>();
}
