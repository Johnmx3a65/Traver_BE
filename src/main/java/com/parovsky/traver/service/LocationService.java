package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsAlreadyExistException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface LocationService {

	List<LocationDTO> getLocations(@Nullable Long categoryId) throws CategoryNotFoundException;

	boolean isLocationExist(Long id);

	LocationDTO getLocationById(@NonNull Long id) throws LocationNotFoundException;

	List<LocationDTO> getFavoriteLocations();

	LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException;

	void addFavoriteLocation(@NonNull Long locationId) throws LocationNotFoundException, FavouriteLocationIsAlreadyExistException;

	LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException;

	void deleteLocation(@NonNull Long id) throws LocationNotFoundException;

	void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException;

	List<String> getPhotos(@NonNull Long id) throws LocationNotFoundException;

	PhotoView addLocationPhoto(@NonNull PhotoDTO photoDTO, @NonNull Long locationId) throws LocationNotFoundException;

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
