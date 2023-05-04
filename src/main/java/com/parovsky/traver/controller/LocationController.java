package com.parovsky.traver.controller;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.PhotoResponse;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsAlreadyExistException;
import com.parovsky.traver.exception.impl.FavouriteLocationIsNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parovsky.traver.utils.ModelMapper.mapPhotoDTO;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationController {

	private final LocationService locationService;

	@ResponseBody
	@GetMapping("/locations")
	public List<LocationDTO> getLocations(@RequestParam(required = false) Long categoryId) throws CategoryNotFoundException {
		return locationService.getLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("location/{id}/photos")
	public List<String> getPhotos(@PathVariable Long id) throws LocationNotFoundException {
		return locationService.getPhotos(id);
	}

	@ResponseBody
	@GetMapping("/locations/favourite")
	public List<LocationDTO> getFavouriteLocations() {
		return locationService.getFavoriteLocations();
	}

	@ResponseBody
	@GetMapping("/location/{id}")
	public LocationDTO getLocation(@PathVariable Long id) throws LocationNotFoundException {
		return locationService.getLocationById(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/location", consumes = "application/json")
    public LocationDTO saveLocation(@RequestBody LocationDTO locationDTO) throws CategoryNotFoundException {
        return locationService.saveLocation(locationDTO);
    }

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/location/favourite/{locationId}")
	public void addFavouriteLocation(@PathVariable Long locationId) throws FavouriteLocationIsAlreadyExistException, LocationNotFoundException {
		locationService.addFavoriteLocation(locationId);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/location/{id}/photo", consumes = "application/json")
	public PhotoResponse addLocationPhoto(@PathVariable(name = "id") Long locationId, @RequestBody PhotoDTO photoDTO) throws LocationNotFoundException {
		return mapPhotoDTO(locationService.addLocationPhoto(photoDTO, locationId));
	}

	@ResponseBody
	@PutMapping(value = "/location", consumes = "application/json")
	public LocationDTO updateLocation(@RequestBody LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
		return locationService.updateLocation(locationDTO);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/favourite/{locationId}")
	public void deleteFavouriteLocation(@PathVariable Long locationId) throws FavouriteLocationIsNotFoundException {
		locationService.deleteFavoriteLocation(locationId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/{id}")
	public void deleteLocation(@PathVariable long id) throws LocationNotFoundException {
		locationService.deleteLocation(id);
	}
}
