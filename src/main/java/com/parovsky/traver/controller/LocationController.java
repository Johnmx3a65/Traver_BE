package com.parovsky.traver.controller;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.PhotoResponse;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.exception.impl.*;
import com.parovsky.traver.service.CategoryService;
import com.parovsky.traver.service.LocationService;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parovsky.traver.utils.ModelMapper.mapPhotoDTO;

@RestController
public class LocationController {

	private final LocationService locationService;

	private final UserService userService;

	private final CategoryService categoryService;

	@Autowired
	public LocationController(LocationService locationService, UserService userService, CategoryService categoryService) {
		this.locationService = locationService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@GetMapping("/locations")
	public ResponseEntity<List<LocationDTO>> getLocations(@RequestParam(required = false) Long categoryId) throws CategoryNotFoundException {
		if (categoryId != null) {
			if (categoryService.isCategoryExistById(categoryId)) {
				List<LocationDTO> locations = locationService.getLocationsByCategoryId(categoryId);
				return new ResponseEntity<>(locations, HttpStatus.OK);
			} else {
				throw new CategoryNotFoundException();
			}
		}
		List<LocationDTO> locations = locationService.getAllLocations();
		return new ResponseEntity<>(locations, HttpStatus.OK);
	}

	@GetMapping("location/{id}/photos")
	public ResponseEntity<List<String>> getPhotos(@PathVariable Long id) throws LocationNotFoundException {
		if (locationService.isLocationExist(id)) {
			List<String> photos = locationService.getPhotos(id);
			return new ResponseEntity<>(photos, HttpStatus.OK);
		} else {
			throw new LocationNotFoundException();
		}
	}

	@GetMapping("/locations/favourite")
	public ResponseEntity<List<LocationDTO>> getFavouriteLocations() throws UserNotFoundException {
		String userEmail = userService.getCurrentUserEmail();
		if (userService.isUserExistByEmail(userEmail)) {
			UserDTO user = userService.getUserByEmail(userEmail);
			List<LocationDTO> locations = locationService.getFavoriteLocations(user.getId());
			return new ResponseEntity<>(locations, HttpStatus.OK);
		} else {
			throw new UserNotFoundException();
		}
	}

	@GetMapping("/location/{id}")
	public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) throws LocationNotFoundException {
		if (locationService.isLocationExist(id)) {
			LocationDTO location = locationService.getLocationById(id);
			return new ResponseEntity<>(location, HttpStatus.OK);
		} else {
			throw new LocationNotFoundException();
		}
	}

    @PostMapping(value = "/location", consumes = "application/json")
    public ResponseEntity<LocationDTO> saveLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO location = locationService.saveLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

	@PostMapping(value = "/location/favourite/{locationId}")
	public ResponseEntity<Void> addFavouriteLocation(@PathVariable Long locationId) throws UserNotFoundException, FavouriteLocationIsAlreadyExistException {
		String userEmail = userService.getCurrentUserEmail();
		if (!userService.isUserExistByEmail(userEmail)) {
			throw new UserNotFoundException();
		}
		UserDTO user = userService.getUserByEmail(userEmail);
		if (locationService.isFavouriteLocationExist(user.getId(), locationId)) {
			throw new FavouriteLocationIsAlreadyExistException();
		}
		locationService.addFavoriteLocation(locationId, user.getId());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping(value = "/location/{id}/photo", consumes = "application/json")
	public ResponseEntity<PhotoResponse> addLocationPhoto(@PathVariable(name = "id") Long locationId, @RequestBody PhotoDTO photoDTO) throws LocationNotFoundException {
		if (!locationService.isLocationExist(locationId)) {
			throw new LocationNotFoundException();
		}
		return new ResponseEntity<>(mapPhotoDTO(locationService.addLocationPhoto(photoDTO, locationId)), HttpStatus.CREATED);
	}

	@PutMapping(value = "/location", consumes = "application/json")
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
		if (!locationService.isLocationExist(locationDTO.getId())) {
			throw new LocationNotFoundException();
		} else if (!categoryService.isCategoryExistById(locationDTO.getCategoryId())) {
			throw new CategoryNotFoundException();
		}
		LocationDTO location = locationService.updateLocation(locationDTO);
		return new ResponseEntity<>(location, HttpStatus.OK);
	}

	@DeleteMapping(value = "/location/favourite/{locationId}")
	public ResponseEntity<Void> deleteFavouriteLocation(@PathVariable Long locationId) throws UserNotFoundException, LocationNotFoundException, FavouriteLocationIsNotFoundException {
		if (!locationService.isLocationExist(locationId)) {
			throw new LocationNotFoundException();
		}
		String userEmail = userService.getCurrentUserEmail();
		if (!userService.isUserExistByEmail(userEmail)) {
			throw new UserNotFoundException();
		}
		UserDTO user = userService.getUserByEmail(userEmail);
		if (!locationService.isFavouriteLocationExist(user.getId(), locationId)) {
			throw new FavouriteLocationIsNotFoundException();

		}
		locationService.deleteFavoriteLocation(locationId, user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/location/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable long id) throws LocationNotFoundException {
		if (!locationService.isLocationExist(id)) {
			throw new LocationNotFoundException();
		}
		locationService.deleteLocation(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
