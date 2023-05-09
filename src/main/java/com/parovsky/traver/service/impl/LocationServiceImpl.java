package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.FavouriteLocation;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsAlreadyExistException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.service.LocationService;
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

	private final PhotoDAO photoDAO;

	private final UserDAO userDAO;

	private final ModelMapper modelMapper;

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
		return result.stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
	}

	@Override
	public boolean isLocationExist(Long id) {
		return locationDAO.isLocationExist(id);
	}

	@Override
	public LocationDTO getLocationById(@NonNull Long id) throws LocationNotFoundException {
		Location location = locationDAO.getLocationById(id);
		if (location == null) {
			throw new LocationNotFoundException();
		}
		return LocationService.transformLocationToLocationDTO(location);
	}

	@Override
	public List<LocationDTO> getFavoriteLocations() {
		String userEmail = userDAO.getCurrentUserEmail();
		return locationDAO.getFavouriteLocationsByUserEmail(userEmail).stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
	}

	@Override
	public List<String> getPhotos(@NonNull Long id) throws LocationNotFoundException {
		if (!locationDAO.isLocationExist(id)) {
			throw new LocationNotFoundException();
		}
		return photoDAO.findAllByLocationId(id).stream().map(Photo::getUrl).collect(Collectors.toList());
	}

	@Override
	public PhotoView addLocationPhoto(@NonNull PhotoDTO photoDTO, @NonNull Long locationId) throws LocationNotFoundException {
		Location location = locationDAO.getLocationById(locationId);
		if (location == null) {
			throw new LocationNotFoundException();
		}
		Photo photo = photoDAO.addLocationPhoto(photoDTO, location);
		return modelMapper.map(photo, PhotoView.class);
	}

	@Override
	public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException {
		Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		return LocationService.transformLocationToLocationDTO(locationDAO.saveLocation(locationDTO, category));
	}

	@Override
	public void addFavoriteLocation(@NonNull Long locationId) throws LocationNotFoundException, FavouriteLocationIsAlreadyExistException {
		String email = userDAO.getCurrentUserEmail();
		if (!locationDAO.isFavouriteLocationExist(email, locationId)) {
			throw new FavouriteLocationIsAlreadyExistException();
		}
		FavouriteLocation favouriteLocation = locationDAO.addFavouriteLocation(email, locationId);
		if (favouriteLocation == null) {
			throw new LocationNotFoundException();
		}
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
		return LocationService.transformLocationToLocationDTO(locationDAO.updateLocation(locationDTO, category));
	}

	@Override
	public void deleteLocation(@NonNull Long id) throws LocationNotFoundException {
		if (!isLocationExist(id)) {
			throw new LocationNotFoundException();
		}
		locationDAO.deleteLocation(id);
	}

	@Override
	public void deleteFavoriteLocation(@NonNull Long locationId) throws FavouriteLocationIsNotFoundException {
		String email = userDAO.getCurrentUserEmail();
		if (!locationDAO.isFavouriteLocationExist(email, locationId)) {
			throw new FavouriteLocationIsNotFoundException();
		}
		locationDAO.deleteFavouriteLocation(email, locationId);
	}
}
