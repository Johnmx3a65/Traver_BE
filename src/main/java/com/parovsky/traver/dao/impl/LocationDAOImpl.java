package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.repository.FavouriteLocationRepository;
import com.parovsky.traver.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional
public class LocationDAOImpl implements LocationDAO {

	private final LocationRepository locationRepository;

	private final FavouriteLocationRepository favouriteLocationRepository;

	@Autowired
	public LocationDAOImpl(LocationRepository locationRepository, FavouriteLocationRepository favouriteLocationRepository) {
		this.locationRepository = locationRepository;
		this.favouriteLocationRepository = favouriteLocationRepository;
	}

	@Override
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}

	public List<Location> getLocationsByCategoryId(Long categoryId) {
		return locationRepository.findAllByCategoryId(categoryId);
	}

	@Override
	public @Nullable Location getLocationById(@NonNull Long id) {
		return locationRepository.findById(id).orElse(null);
	}

	@Override
	public boolean isLocationExist(Long id) {
		return locationRepository.existsById(id);
	}

	@Override
	public boolean isFavouriteLocationExist(String email, Long locationId) {
		return favouriteLocationRepository.existsByUserEmailAndLocationId(email, locationId);
	}

	@Override
	public Location saveLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) {
		Location location = new Location();
		fillProperties(locationDTO, category, location);
		return locationRepository.saveAndFlush(location);
	}

	@Override
	public Location updateLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) {
		Location location = locationRepository.getById(locationDTO.getId());
		fillProperties(locationDTO, category, location);
		return locationRepository.saveAndFlush(location);
	}

	@Override
	public void deleteLocation(Long id) {
		locationRepository.deleteById(id);
	}

	@Override
	public List<Location> getFavouriteLocationsByUserEmail(String email) {
		return favouriteLocationRepository.findAllLocationsByUserEmail(email);
	}

	@Override
	public List<Location> getFavouriteLocationsByUserEmailAndCategoryId(String email, Long categoryId) {
		return favouriteLocationRepository.findAllLocationsByUserEmailAndCategoryId(email, categoryId);
	}

	@Override
	public void addFavouriteLocation(String email, Long locationId) {
		favouriteLocationRepository.save(email, locationId);
		favouriteLocationRepository.flush();
	}

	@Override
	public void deleteFavouriteLocation(String email, Long locationId) {
		favouriteLocationRepository.delete(email, locationId);
		favouriteLocationRepository.flush();
	}

	private void fillProperties(@NonNull LocationDTO locationDTO, @NonNull Category category, Location location) {
		location.setName(locationDTO.getName());
		location.setSubtitle(locationDTO.getSubtitle());
		location.setDescription(locationDTO.getDescription());
		location.setCoordinates(locationDTO.getCoordinates());
		location.setPicture(locationDTO.getPicture());
		location.setCategory(category);
	}
}
