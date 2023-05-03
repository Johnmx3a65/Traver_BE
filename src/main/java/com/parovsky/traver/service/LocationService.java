package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LocationService {
	List<LocationDTO> getAllLocations();

	List<LocationDTO> getLocationsByCategoryId(Long categoryId);

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

	PhotoDTO addLocationPhoto(PhotoDTO photoDTO, Long locationId);

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

	static PhotoDTO mapPhotoToPhotoDTO(Photo photo) {
		return PhotoDTO.builder()
				.id(photo.getId())
				.url(photo.getUrl())
				.locationId(photo.getLocation().getId())
				.build();
	}
}
