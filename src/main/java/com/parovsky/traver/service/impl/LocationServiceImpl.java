package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
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

	private final UserDao userDao;

	@Override
	public List<LocationDTO> getLocations(@Nullable Long categoryId) throws CategoryNotFoundException {
		List<Location> result;
		if (categoryId != null) {
			if (!categoryDAO.isExistById(categoryId)) {
				throw new CategoryNotFoundException();
			}
			result = locationDAO.getAllByCategoryId(categoryId);
		} else {
			result = locationDAO.getAll();
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationDTO getLocationById(@NonNull Long id) throws LocationNotFoundException, UserNotFoundException {
		Location location = locationDAO.get(id).orElseThrow(LocationNotFoundException::new);
		LocationDTO locationDTO = modelMapper.map(location, LocationDTO.class);
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteExist(user.getEmail(), location.getId())) {
			locationDTO.setIsFavorite(true);
		}
		return locationDTO;
	}

	@Override
	public List<LocationDTO> getFavoriteLocations(@Nullable Long categoryId) throws CategoryNotFoundException, UserNotFoundException {
		List<Location> result;
		UserView currentUser = userService.getCurrentUser();
		User user = userDao.getByEmail(currentUser.getEmail()).orElseThrow(UserNotFoundException::new);
		if (categoryId == null) {
			result = locationDAO.getFavouritesByUser(user);
		} else {
			Category category = categoryDAO.get(categoryId).orElseThrow(CategoryNotFoundException::new);
			result = locationDAO.getFavouritesByUserAndCategory(user, category);
		}
		return result
				.stream()
				.map(l -> modelMapper.map(l, LocationDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException {
		Category category = categoryDAO.get(locationDTO.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
		Location location = Location.builder()
				.name(locationDTO.getName())
				.subtitle(locationDTO.getSubtitle())
				.description(locationDTO.getDescription())
				.picture(locationDTO.getPicture())
				.coordinates(locationDTO.getCoordinates())
				.category(category)
				.build();
		Location result = locationDAO.save(location);
		return modelMapper.map(result, LocationDTO.class);
	}

	@Override
	public void addFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsAlreadyExistException, UserNotFoundException {
		UserView user = userService.getCurrentUser();
		if (locationDAO.isFavouriteExist(user.getEmail(), locationId)) {
			throw new FavouriteLocationIsAlreadyExistException();
		}
		locationDAO.addFavourite(user.getEmail(), locationId);
	}

	@Override
	public LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
		Category category = categoryDAO.get(locationDTO.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
		Location location = locationDAO.get(locationDTO.getId()).orElseThrow(LocationNotFoundException::new);

		location.setName(locationDTO.getName());
		location.setSubtitle(locationDTO.getSubtitle());
		location.setDescription(locationDTO.getDescription());
		location.setPicture(locationDTO.getPicture());
		location.setCoordinates(locationDTO.getCoordinates());
		location.setCategory(category);

		Location result = locationDAO.save(location);
		return modelMapper.map(result, LocationDTO.class);
	}

	@Override
	public void deleteLocation(@NonNull Long id) throws LocationNotFoundException {
		Location location = locationDAO.get(id).orElseThrow(LocationNotFoundException::new);
		locationDAO.delete(location);
	}

	@Override
	public void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException, UserNotFoundException {
		UserView user = userService.getCurrentUser();
		if (!locationDAO.isFavouriteExist(user.getEmail(), locationId)) {
			throw new FavouriteLocationIsNotFoundException();
		}
		locationDAO.deleteFavourite(user.getEmail(), locationId);
	}
}
