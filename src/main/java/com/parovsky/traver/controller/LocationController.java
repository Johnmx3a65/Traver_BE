package com.parovsky.traver.controller;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationController {

	private final LocationService locationService;

	@ResponseBody
	@GetMapping("/locations")
	public List<LocationDTO> getLocations(@RequestParam(required = false) Long categoryId) throws EntityNotFoundException {
		return locationService.getLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/locations/favourite")
	public List<LocationDTO> getFavouriteLocations(@RequestParam(required = false) Long categoryId) throws EntityNotFoundException {
		return locationService.getFavoriteLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/location/{id}")
	public LocationDTO getLocation(@PathVariable Long id) throws EntityNotFoundException {
		return locationService.getLocationById(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/location", consumes = "application/json")
    public LocationDTO saveLocation(@RequestBody LocationDTO locationDTO) throws EntityNotFoundException {
        return locationService.saveLocation(locationDTO);
    }

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/location/favourite/{locationId}")
	public void addFavouriteLocation(@PathVariable Long locationId) throws EntityAlreadyExistsException, EntityNotFoundException {
		locationService.addFavoriteLocation(locationId);
	}

	@ResponseBody
	@PutMapping(value = "/location", consumes = "application/json")
	public LocationDTO updateLocation(@RequestBody LocationDTO locationDTO) throws EntityNotFoundException {
		return locationService.updateLocation(locationDTO);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/favourite/{locationId}")
	public void deleteFavouriteLocation(@PathVariable Long locationId) throws EntityNotFoundException {
		locationService.deleteFavoriteLocation(locationId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/{id}")
	public void deleteLocation(@PathVariable long id) throws EntityNotFoundException {
		locationService.deleteLocation(id);
	}
}
