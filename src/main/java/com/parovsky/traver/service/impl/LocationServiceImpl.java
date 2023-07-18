package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.LocationService;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationServiceImpl implements LocationService {

	private final LocationDAO locationDAO;

	private final CategoryDAO categoryDAO;

	private final ModelMapper modelMapper;

	private final UserService userService;

	private final UserDao userDao;

	@Override
	public List<LocationView> getLocations(@Nullable Long categoryId) throws EntityNotFoundException {
		List<Location> result;
		if (categoryId != null) {
			if (!categoryDAO.isExistById(categoryId)) {
				throw new EntityNotFoundException("Category not found");
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
	public LocationView getLocationById(@NonNull Long id) throws EntityNotFoundException {
		Location location = locationDAO.get(id).orElseThrow(() -> new EntityNotFoundException("Location not found"));
		LocationView locationView = modelMapper.map(location, LocationView.class);
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteExist(user.getEmail(), location.getId())) {
			locationView.setIsFavorite(true);
		}
		return locationView;
	}

	@Override
	public List<LocationView> getFavoriteLocations(@Nullable Long categoryId) throws EntityNotFoundException {
		List<Location> result;
		UserView currentUser = userService.getCurrentUser();
		User user = userDao.getByEmail(currentUser.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		if (categoryId == null) {
			result = locationDAO.getFavouritesByUser(user);
		} else {
			Category category = categoryDAO.get(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));
			result = locationDAO.getFavouritesByUserAndCategory(user, category);
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationView.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationView saveLocation(@Valid @NonNull SaveLocationModel model) throws EntityNotFoundException, EntityAlreadyExistsException {
		if (locationDAO.isLocationExist(model.getName(), model.getSubtitle())) {
			throw new EntityAlreadyExistsException("Location already exist");
		}
		Category category = categoryDAO.get(model.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
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
	public void addFavoriteLocation(@NonNull Long locationId) throws EntityAlreadyExistsException, EntityNotFoundException {
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteExist(user.getEmail(), locationId)) {
			throw new EntityAlreadyExistsException("Favorite location already exist");
		}
		locationDAO.addFavourite(user.getEmail(), locationId);
	}

	@Override
	public LocationView updateLocation(@NonNull @Valid UpdateLocationModel model) throws EntityNotFoundException {
		Category category = categoryDAO.get(model.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));
		Location location = locationDAO.get(model.getId()).orElseThrow(() -> new EntityNotFoundException("Location not found"));

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
	public void deleteLocation(@NonNull Long id) throws EntityNotFoundException {
		Location location = locationDAO.get(id).orElseThrow(() -> new EntityNotFoundException("Location not found"));
		locationDAO.delete(location);
	}

	@Override
	public void deleteFavoriteLocation(@NonNull Long locationId) throws EntityNotFoundException {
		UserView user = userService.getCurrentUser();
		if (!locationDAO.isFavouriteExist(user.getEmail(), locationId)) {
			throw new EntityNotFoundException("Location is not favorite");
		}
		locationDAO.deleteFavourite(user.getEmail(), locationId);
	}
}
