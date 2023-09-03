package com.parovsky.traver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name= "location_user")
@IdClass(FavouriteLocation.FavouriteLocationPK.class)
public class FavouriteLocation {

	@Id
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Id
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
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
