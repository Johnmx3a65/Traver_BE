package com.parovsky.traver.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public static class FavouriteLocationPK implements Serializable {

		protected User user;

		protected Location location;

		public FavouriteLocationPK() {}

		public FavouriteLocationPK(User user, Location location) {
			this.user = user;
			this.location = location;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			FavouriteLocationPK that = (FavouriteLocationPK) o;
			return Objects.equals(user, that.user) &&
					Objects.equals(location, that.location);
		}

		@Override
		public int hashCode() {
			return Objects.hash(user, location);
		}

	}
}
