package com.parovsky.traver.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "url", nullable = false, columnDefinition="TEXT")
    private String url;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Photo photo = (Photo) o;
        return getId() != null && Objects.equals(getId(), photo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
