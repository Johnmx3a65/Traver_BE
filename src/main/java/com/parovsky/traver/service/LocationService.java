package com.parovsky.traver.service;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.List;

public interface LocationService {

	List<LocationDTO> getLocations(@Nullable Long categoryId) throws EntityNotFoundException;

	LocationDTO getLocationById(@NonNull Long id) throws EntityNotFoundException;

	List<LocationDTO> getFavoriteLocations(@Nullable Long categoryId) throws EntityNotFoundException;

	LocationDTO saveLocation(@Valid @NonNull SaveLocationModel model) throws EntityNotFoundException, EntityAlreadyExistsException;

	void addFavoriteLocation(@NonNull Long locationId) throws EntityAlreadyExistsException, EntityNotFoundException;

	LocationDTO updateLocation(@Valid @NonNull UpdateLocationModel model) throws EntityNotFoundException;

	void deleteLocation(@NonNull Long id) throws EntityNotFoundException;

	void deleteFavoriteLocation(@NonNull Long locationId) throws EntityNotFoundException;
}
