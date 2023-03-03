package com.parovsky.traver.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name="users_favourite_locations")
@IdClass(FavouriteLocation.FavouriteLocationPK.class)
public class FavouriteLocation {

	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@EqualsAndHashCode
	public static class FavouriteLocationPK implements Serializable {

		protected User user;

		protected Location location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		FavouriteLocation that = (FavouriteLocation) o;
		return getUser() != null && Objects.equals(getUser(), that.getUser())
				&& getLocation() != null && Objects.equals(getLocation(), that.getLocation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, location);
	}
}
