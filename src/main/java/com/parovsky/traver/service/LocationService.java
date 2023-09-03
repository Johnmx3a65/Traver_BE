package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import java.util.List;

public interface LocationService {

	List<LocationView> getLocations(@Nullable Long categoryId);

	LocationView getLocationById(@NonNull Long id, UserDetails userDetails);

	List<LocationView> getFavoriteLocations(@Nullable Long categoryId, UserDetails userDetails);

	LocationView saveLocation(@Valid @NonNull SaveLocationModel model);

	void addFavoriteLocation(@NonNull Long locationId, UserDetails userDetails);

	LocationView updateLocation(@Valid @NonNull UpdateLocationModel model);

	void deleteLocation(@NonNull Long id);

	void deleteFavoriteLocation(@NonNull Long locationId, UserDetails userDetails);
}
