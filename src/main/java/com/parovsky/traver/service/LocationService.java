package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Location;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LocationService {
	List<LocationDTO> getAllLocations();

	boolean isLocationExist(Long id);

	boolean isFavouriteLocationExist(Long userId, Long locationId);

	LocationDTO getLocationById(Long id);

	List<LocationDTO> getFavoriteLocations(Long userId);

	LocationDTO saveLocation(@NonNull LocationDTO locationDTO);

	void addFavoriteLocation(Long locationId, Long userId);

	LocationDTO updateLocation(@NonNull LocationDTO locationDTO);

	void deleteLocation(Long id);

	void deleteFavoriteLocation(Long locationId, Long userId);

	List<String> getPhotos(Long id);

	static LocationDTO transformLocationToLocationDTO(Location location) {
		return new LocationDTO(
				location.getId(),
				location.getName(),
				location.getDescription(),
				location.getCoordinates(),
				location.getCategory().getId(),
				location.getPicture(),
				location.getSubtitle()
		);
	}
}
