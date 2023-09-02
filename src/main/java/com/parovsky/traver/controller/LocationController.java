package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.SaveLocationModel;
import com.parovsky.traver.dto.model.UpdateLocationModel;
import com.parovsky.traver.dto.view.LocationView;
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
	public List<LocationView> getLocations(@RequestParam(required = false) Long categoryId) {
		return locationService.getLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/locations/favourite")
	public List<LocationView> getFavouriteLocations(@RequestParam(required = false) Long categoryId) {
		return locationService.getFavoriteLocations(categoryId);
	}

	@ResponseBody
	@GetMapping("/location/{id}")
	public LocationView getLocation(@PathVariable Long id) {
		return locationService.getLocationById(id);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/location", consumes = "application/json")
    public LocationView saveLocation(@Valid @RequestBody SaveLocationModel model) {
        return locationService.saveLocation(model);
    }

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/location/favourite/{locationId}")
	public void addFavouriteLocation(@PathVariable Long locationId) {
		locationService.addFavoriteLocation(locationId);
	}

	@ResponseBody
	@PutMapping(value = "/location", consumes = "application/json")
	public LocationView updateLocation(@Valid @RequestBody UpdateLocationModel model) {
		return locationService.updateLocation(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/favourite/{locationId}")
	public void deleteFavouriteLocation(@PathVariable Long locationId) {
		locationService.deleteFavoriteLocation(locationId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/{id}")
	public void deleteLocation(@PathVariable long id) {
		locationService.deleteLocation(id);
	}
}
