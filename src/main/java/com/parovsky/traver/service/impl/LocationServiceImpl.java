package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

	private final LocationDAO locationDAO;

	private final CategoryDAO categoryDAO;

	private final PhotoDAO photoDAO;

	@Autowired
	public LocationServiceImpl(LocationDAO locationDAO, CategoryDAO categoryDAO, PhotoDAO photoDAO) {
		this.locationDAO = locationDAO;
		this.categoryDAO = categoryDAO;
		this.photoDAO = photoDAO;
	}

	@Override
	public List<LocationDTO> getAllLocations() {
		List<Location> locations = locationDAO.getAllLocations();
		return locations.stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
	}

	public List<LocationDTO> getLocationsByCategoryId(Long categoryId) {
		List<Location> locations = locationDAO.getLocationsByCategoryId(categoryId);
		return locations.stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
	}

	@Override
	public boolean isLocationExist(Long id) {
		return locationDAO.isLocationExist(id);
	}

	@Override
	public boolean isFavouriteLocationExist(Long userId, Long locationId) {
		return locationDAO.isFavouriteLocationExist(userId, locationId);
	}

	@Override
	public LocationDTO getLocationById(Long id) {
		Location location = locationDAO.getLocationById(id);
		return LocationService.transformLocationToLocationDTO(location);
	}

	public List<LocationDTO> getFavoriteLocations(Long userId) {
		return locationDAO.getFavouriteLocationsByUserId(userId).stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
	}

	@Override
	public List<String> getPhotos(Long id) {
		return photoDAO.findAllByLocationId(id).stream().map(Photo::getUrl).collect(Collectors.toList());
	}

	public PhotoDTO addLocationPhoto(PhotoDTO photoDTO, Long locationId) {
		Location location = locationDAO.getLocationById(locationId);
		return LocationService.mapPhotoToPhotoDTO(photoDAO.addLocationPhoto(photoDTO, location));
	}

	public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) {
		Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
		return LocationService.transformLocationToLocationDTO(locationDAO.saveLocation(locationDTO, category));
	}

	public void addFavoriteLocation(Long locationId, Long userId) {
		locationDAO.addFavouriteLocation(userId, locationId);
	}

	@Override
	public LocationDTO updateLocation(@NonNull LocationDTO locationDTO) {
		Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
		return LocationService.transformLocationToLocationDTO(locationDAO.updateLocation(locationDTO, category));
	}

	@Override
	public void deleteLocation(@NonNull Long id) {
		locationDAO.deleteLocation(id);
	}

	public void deleteFavoriteLocation(Long locationId, Long userId) {
		locationDAO.deleteFavouriteLocation(userId, locationId);
	}
}
