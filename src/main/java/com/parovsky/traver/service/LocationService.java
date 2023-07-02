package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.exception.impl.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface LocationService {

	List<LocationDTO> getLocations(@Nullable Long categoryId) throws CategoryNotFoundException;

	LocationDTO getLocationById(@NonNull Long id) throws LocationNotFoundException, UserNotFoundException;

	List<LocationDTO> getFavoriteLocations(@Nullable Long categoryId) throws CategoryNotFoundException, UserNotFoundException;

	LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException;

	void addFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsAlreadyExistException, UserNotFoundException;

	LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException;

	void deleteLocation(@NonNull Long id) throws LocationNotFoundException;

	void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException, UserNotFoundException;
}
