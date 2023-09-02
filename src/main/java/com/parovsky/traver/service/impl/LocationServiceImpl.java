package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.ApplicationException;
import com.parovsky.traver.exception.Errors;
import com.parovsky.traver.service.LocationService;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.parovsky.traver.exception.Errors.*;
import static com.parovsky.traver.utils.Constraints.*;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationServiceImpl implements LocationService {

	private final UserService userService;

	private final LocationDAO locationDAO;

	private final CategoryDAO categoryDAO;

	private final UserDao userDao;

	private final PhotoDAO photoDAO;

	private final ModelMapper modelMapper;

	@Override
	public List<LocationView> getLocations(@Nullable Long categoryId) {
		List<Location> result;
		if (categoryId != null) {
			if (!categoryDAO.isExistById(categoryId)) {
				throw new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, categoryId));
			}
			result = locationDAO.getAllByCategoryId(categoryId);
		} else {
			result = locationDAO.getAll();
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationView.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationView getLocationById(@NonNull Long id) {
		Location location = locationDAO.get(id).orElseThrow(
				() -> new ApplicationException(LOCATION_NOT_FOUND, Collections.singletonMap(ID, id)));
		LocationView locationView = modelMapper.map(location, LocationView.class);
		String email = userService.getCurrentUser().getEmail();
		if (locationDAO.isFavouriteExist(email, location.getId())) {
			locationView.setIsFavorite(true);
		}
		return locationView;
	}

	@Override
	public List<LocationView> getFavoriteLocations(@Nullable Long categoryId) {
		List<Location> result;
		String email = userService.getCurrentUser().getEmail();
		User user = userDao.getByEmail(email).orElseThrow(
				() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, email)));
		if (categoryId == null) {
			result = locationDAO.getFavouritesByUser(user);
		} else {
			Category category = categoryDAO.get(categoryId).orElseThrow(() ->
					new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, categoryId)));
			result = locationDAO.getFavouritesByUserAndCategory(user, category);
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationView.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationView saveLocation(@Valid @NonNull SaveLocationModel model) {
		if (locationDAO.isLocationExist(model.getName(), model.getSubtitle())) {
			Map<String, Object> params = new HashMap<>();
			params.put("name", model.getName());
			params.put("subtitle", model.getSubtitle());
			throw new ApplicationException(Errors.LOCATION_ALREADY_EXIST, params);
		}
		Category category = categoryDAO
				.get(model.getCategoryId())
				.orElseThrow(() -> new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, model.getCategoryId())));
		Location location = Location.builder()
				.name(model.getName())
				.subtitle(model.getSubtitle())
				.description(model.getDescription())
				.picture(model.getPicture())
				.coordinates(model.getCoordinates())
				.category(category)
				.build();
		Location result = locationDAO.save(location);
		return modelMapper.map(result, LocationView.class);
	}

	@Override
	public void addFavoriteLocation(@NonNull Long locationId) {
		String email = userService.getCurrentUser().getEmail();
		if (locationDAO.isFavouriteExist(email, locationId)) {
			throw new ApplicationException(FAVORITE_LOCATION_ALREADY_EXIST, Collections.singletonMap(LOCATION_ID, locationId));
		}
		locationDAO.addFavourite(email, locationId);
	}

	@Override
	public LocationView updateLocation(@NonNull @Valid UpdateLocationModel model) {
		Category category = categoryDAO.get(model.getCategoryId()).orElseThrow(() -> new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, model.getCategoryId())));
		Location location = locationDAO.get(model.getId()).orElseThrow(() -> new ApplicationException(LOCATION_NOT_FOUND, Collections.singletonMap(ID, model.getId())));

		location.setName(model.getName());
		location.setSubtitle(model.getSubtitle());
		location.setDescription(model.getDescription());
		location.setPicture(model.getPicture());
		location.setCoordinates(model.getCoordinates());
		location.setCategory(category);

		Location result = locationDAO.save(location);
		return modelMapper.map(result, LocationView.class);
	}

	@Override
	public void deleteLocation(@NonNull Long id) {
		Location location = locationDAO.get(id).orElseThrow(() -> new ApplicationException(LOCATION_NOT_FOUND, Collections.singletonMap(ID, id)));
		if (photoDAO.isPhotoExistsByLocationId(id)) {
			throw new ApplicationException(LOCATION_HAS_PHOTOS, Collections.singletonMap(ID, id));
		}
		locationDAO.delete(location);
	}

	@Override
	public void deleteFavoriteLocation(@NonNull Long locationId) {
		String email = userService.getCurrentUser().getEmail();
		if (!locationDAO.isFavouriteExist(email, locationId)) {
			throw new ApplicationException(Errors.FAVORITE_LOCATION_NOT_FOUND, Collections.singletonMap(LOCATION_ID, locationId));
		}
		locationDAO.deleteFavourite(email, locationId);
	}
}
