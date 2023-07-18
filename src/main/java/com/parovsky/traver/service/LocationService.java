package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.List;

public interface LocationService {

	List<LocationView> getLocations(@Nullable Long categoryId) throws EntityNotFoundException;

	LocationView getLocationById(@NonNull Long id) throws EntityNotFoundException;

	List<LocationView> getFavoriteLocations(@Nullable Long categoryId) throws EntityNotFoundException;

	LocationView saveLocation(@Valid @NonNull SaveLocationModel model) throws EntityNotFoundException, EntityAlreadyExistsException;

	void addFavoriteLocation(@NonNull Long locationId) throws EntityAlreadyExistsException, EntityNotFoundException;

	LocationView updateLocation(@Valid @NonNull UpdateLocationModel model) throws EntityNotFoundException;

	void deleteLocation(@NonNull Long id) throws EntityNotFoundException;

	void deleteFavoriteLocation(@NonNull Long locationId) throws EntityNotFoundException;
}
