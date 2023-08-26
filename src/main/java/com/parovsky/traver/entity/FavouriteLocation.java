package com.parovsky.traver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FavouriteLocationPK implements Serializable {

		protected User user;

		protected Location location;
	}
}
