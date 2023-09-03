package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.SaveLocationForm;
import com.parovsky.traver.dto.form.UpdateLocationForm;
import com.parovsky.traver.dto.view.LocationView;
import com.parovsky.traver.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
	public List<LocationView> getFavouriteLocations(@RequestParam(required = false) Long categoryId, @AuthenticationPrincipal() UserDetails userDetails) {
		return locationService.getFavoriteLocations(categoryId, userDetails);
	}

	@ResponseBody
	@GetMapping("/location/{id}")
	public LocationView getLocation(@PathVariable Long id, @AuthenticationPrincipal() UserDetails userDetails) {
		return locationService.getLocationById(id, userDetails);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/location", consumes = "application/json")
    public LocationView saveLocation(@Valid @RequestBody SaveLocationForm model) {
        return locationService.saveLocation(model);
    }

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/location/favourite/{locationId}")
	public void addFavouriteLocation(@PathVariable Long locationId, @AuthenticationPrincipal() UserDetails userDetails) {
		locationService.addFavoriteLocation(locationId, userDetails);
	}

	@ResponseBody
	@PutMapping(value = "/location", consumes = "application/json")
	public LocationView updateLocation(@Valid @RequestBody UpdateLocationForm model) {
		return locationService.updateLocation(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/favourite/{locationId}")
	public void deleteFavouriteLocation(@PathVariable Long locationId, @AuthenticationPrincipal() UserDetails userDetails) {
		locationService.deleteFavoriteLocation(locationId, userDetails);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/location/{id}")
	public void deleteLocation(@PathVariable long id) {
		locationService.deleteLocation(id);
	}
}
