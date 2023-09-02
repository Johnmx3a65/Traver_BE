package com.parovsky.traver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table()
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "location_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String coordinates;

    @Column(nullable = false, columnDefinition="TEXT")
    private String picture;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<FavouriteLocation> favouriteLocations;
}
