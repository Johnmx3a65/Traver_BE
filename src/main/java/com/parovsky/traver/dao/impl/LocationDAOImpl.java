package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.FavouriteLocation;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.repository.FavouriteLocationRepository;
import com.parovsky.traver.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationDAOImpl implements LocationDAO {

	private final LocationRepository locationRepository;

	private final FavouriteLocationRepository favouriteLocationRepository;

	@Override
	public List<Location> getAll() {
		return locationRepository.findAll();
	}

	public List<Location> getAllByCategoryId(Long categoryId) {
		return locationRepository.findAllByCategoryId(categoryId);
	}

	@Override
	public Optional<Location> get(@NonNull Long id) {
		return locationRepository.findById(id);
	}

	@Override
	public boolean isFavouriteExist(String email, Long locationId) {
		return favouriteLocationRepository.existsByUserEmailAndLocationId(email, locationId);
	}

	@Override
	public boolean isLocationExist(String name, String subtitle) {
		return locationRepository.existsByNameAndSubtitle(name, subtitle);
	}

	@Override
	public boolean isLocationExistsByCategoryId(Long categoryId) {
		return locationRepository.existsByCategoryId(categoryId);
	}

	@Override
	public Location save(@NonNull Location location) {
		return locationRepository.saveAndFlush(location);
	}

	@Override
	public void delete(@NonNull Location location) {
		locationRepository.deleteById(location.getId());
	}

	@Override
	public List<Location> getFavouritesByUser(User user) {
		return favouriteLocationRepository
				.findAllByUser(user)
				.stream()
				.map(FavouriteLocation::getLocation)
				.collect(Collectors.toList());
	}

	@Override
	public List<Location> getFavouritesByUserAndCategory(User user, Category category) {
		return favouriteLocationRepository
				.findAllByUserAndLocationCategory(user, category)
				.stream()
				.map(FavouriteLocation::getLocation)
				.collect(Collectors.toList());
	}

	@Override
	public void addFavourite(String email, Long locationId) {
		favouriteLocationRepository.save(email, locationId);
		favouriteLocationRepository.flush();
	}

	@Override
	public void deleteFavourite(String email, Long locationId) {
		favouriteLocationRepository.delete(email, locationId);
		favouriteLocationRepository.flush();
	}
}
