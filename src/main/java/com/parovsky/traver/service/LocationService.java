package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LocationService {
	List<LocationDTO> getAllLocations();

	LocationDTO getLocationById(Long id) throws LocationNotFoundException;

	LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException;

	LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException;

	void deleteLocation(Long id) throws LocationNotFoundException;

	List<String> getPhotos(Long id) throws LocationNotFoundException;

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
