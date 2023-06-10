package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
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

	List<LocationDTO> getFavoriteLocations(@Nullable Long categoryId) throws CategoryNotFoundException;

	LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException;

	void addFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsAlreadyExistException;

	LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException;

	void deleteLocation(@NonNull Long id) throws LocationNotFoundException;

	void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException;
}
