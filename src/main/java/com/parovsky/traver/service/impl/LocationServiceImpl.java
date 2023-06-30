package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.exception.impl.*;
import com.parovsky.traver.service.LocationService;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationServiceImpl implements LocationService {

	private final LocationDAO locationDAO;

	private final CategoryDAO categoryDAO;

	private final ModelMapper modelMapper;

	private final UserService userService;

	@Override
	public List<LocationDTO> getLocations(@Nullable Long categoryId) throws CategoryNotFoundException {
		List<Location> result;
		if (categoryId != null) {
			if (!categoryDAO.isCategoryExistById(categoryId)) {
				throw new CategoryNotFoundException();
			}
			result = locationDAO.getLocationsByCategoryId(categoryId);
		} else {
			result = locationDAO.getAllLocations();
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isLocationExist(Long id) {
		return locationDAO.isLocationExist(id);
	}

	@Override
	public LocationDTO getLocationById(@NonNull Long id) throws LocationNotFoundException, UserNotFoundException {
		Location location = locationDAO.getLocationById(id);
		if (location == null) {
			throw new LocationNotFoundException();
		}
		LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteLocationExist(user.getEmail(), location.getId())) {
			locationDTO.setIsFavorite(true);
		}
		return locationDTO;
	}

	@Override
	public List<LocationDTO> getFavoriteLocations(@Nullable Long categoryId) throws CategoryNotFoundException, UserNotFoundException {
		List<Location> result;
		UserView user = userService.getCurrentUser();
		if (categoryId == null) {
			result = locationDAO.getFavouriteLocationsByUserEmail(user.getEmail());
		} else {
			if (!categoryDAO.isCategoryExistById(categoryId)) {
				throw new CategoryNotFoundException();
			}
			result = locationDAO.getFavouriteLocationsByUserEmailAndCategoryId(user.getEmail(), categoryId);
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException {
		Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		Location result = locationDAO.saveLocation(locationDTO, category);
		return modelMapper.map(result, LocationDTO.class);
	}

	@Override
	public void addFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsAlreadyExistException, UserNotFoundException {
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteLocationExist(user.getEmail(), locationId)) {
			throw new FavouriteLocationIsAlreadyExistException();
		}
		locationDAO.addFavouriteLocation(user.getEmail(), locationId);
	}

	@Override
	public LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
		if (!locationDAO.isLocationExist(locationDTO.getId())) {
			throw new LocationNotFoundException();
		}
		Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		Location result = locationDAO.updateLocation(locationDTO, category);
		return modelMapper.map(result, LocationDTO.class);
	}

	@Override
	public void deleteLocation(@NonNull Long id) throws LocationNotFoundException {
		if (!isLocationExist(id)) {
			throw new LocationNotFoundException();
		}
		locationDAO.deleteLocation(id);
	}

	@Override
	public void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException, UserNotFoundException {
		UserView user = userService.getCurrentUser();
		if (!locationDAO.isFavouriteLocationExist(user.getEmail(), locationId)) {
			throw new FavouriteLocationIsNotFoundException();
		}
		locationDAO.deleteFavouriteLocation(user.getEmail(), locationId);
	}
}
