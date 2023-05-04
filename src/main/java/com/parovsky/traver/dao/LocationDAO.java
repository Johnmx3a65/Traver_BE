package com.parovsky.traver.dao;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.FavouriteLocation;
import com.parovsky.traver.entity.Location;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface LocationDAO {

	List<Location> getAllLocations();

	@Nullable Location getLocationById(@NonNull Long id);

	List<Location> getLocationsByCategoryId(Long categoryId);

	boolean isLocationExist(Long id);

	boolean isFavouriteLocationExist(String email, Long locationId);

	Location saveLocation(@NonNull LocationDTO locationDTO, @NonNull Category category);

	Location updateLocation(@NonNull LocationDTO locationDTO, @NonNull Category category);

	void deleteLocation(Long id);

	List<Location> getFavouriteLocationsByUserEmail(String email);

	FavouriteLocation addFavouriteLocation(String email, Long locationId);

	void deleteFavouriteLocation(String email, Long locationId);

	static Location transformLocationDTO(LocationDTO locationDTO) {
		Location location = new Location();
		location.setId(locationDTO.getId());
		location.setName(locationDTO.getName());
		location.setSubtitle(locationDTO.getSubtitle());
		location.setDescription(locationDTO.getDescription());
		location.setCoordinates(locationDTO.getCoordinates());
		location.setPicture(locationDTO.getPicture());
		return location;
	}
}
