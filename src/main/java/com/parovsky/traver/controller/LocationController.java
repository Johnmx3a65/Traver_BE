package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class LocationController {

	private final LocationService locationService;

	@ResponseBody
	@GetMapping("/locations")
	public List<LocationView> getLocations(@RequestParam(required = false) Long categoryId) throws EntityNotFoundException {
		return locationService.getLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/locations/favourite")
	public List<LocationView> getFavouriteLocations(@RequestParam(required = false) Long categoryId) throws EntityNotFoundException {
		return locationService.getFavoriteLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/location/{id}")
	public LocationView getLocation(@PathVariable Long id) throws EntityNotFoundException {
		return locationService.getLocationById(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/location", consumes = "application/json")
    public LocationView saveLocation(@Valid @RequestBody SaveLocationModel model) throws EntityNotFoundException, EntityAlreadyExistsException {
        return locationService.saveLocation(model);
    }

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/location/favourite/{locationId}")
	public void addFavouriteLocation(@PathVariable Long locationId) throws EntityAlreadyExistsException, EntityNotFoundException {
		locationService.addFavoriteLocation(locationId);
	}

	@ResponseBody
	@PutMapping(value = "/location", consumes = "application/json")
	public LocationView updateLocation(@Valid @RequestBody UpdateLocationModel model) throws EntityNotFoundException {
		return locationService.updateLocation(model);
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
