package com.parovsky.traver.controller;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.exception.impl.*;
import com.parovsky.traver.service.LocationService;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    private final UserService userService;

    @Autowired
    public LocationController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("location/{id}/photos")
    public ResponseEntity<List<String>> getPhotos(@PathVariable Long id) throws LocationNotFoundException {
        List<String> photos = locationService.getPhotos(id);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @GetMapping("/locations/favourite")
    public ResponseEntity<List<LocationDTO>> getFavouriteLocations() throws UserNotFoundException {
        List<LocationDTO> locations = locationService.getFavoriteLocations(userService.getCurrentUserDTO().getId());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<LocationDTO> getLocation(@PathVariable Long id) throws LocationNotFoundException {
        LocationDTO location = locationService.getLocationById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping(value = "/location", consumes = "application/json")
    public ResponseEntity<LocationDTO> saveLocation(@RequestBody LocationDTO locationDTO) throws CategoryNotFoundException {
        LocationDTO location = locationService.saveLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping(value = "/location/favourite/{locationId}")
    public ResponseEntity<Void> addFavouriteLocation(@PathVariable Long locationId) throws UserNotFoundException, FavouriteLocationIsAlreadyExistException {
        Long userId = userService.getCurrentUserDTO().getId();
        if (!locationService.isFavouriteLocationExist(userId, locationId)) {
            locationService.addFavoriteLocation(locationId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            throw new FavouriteLocationIsAlreadyExistException();
        }
    }

    @PutMapping(value = "/location", consumes = "application/json")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
        LocationDTO location = locationService.updateLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping(value = "/location/favourite/{locationId}")
    public ResponseEntity<Void> deleteFavouriteLocation(@PathVariable Long locationId) throws UserNotFoundException, LocationNotFoundException, FavouriteLocationIsNotFoundException {
        Long userId = userService.getCurrentUserDTO().getId();
        if (locationService.isFavouriteLocationExist(userId, locationId)) {
            locationService.deleteFavoriteLocation(locationId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            throw new FavouriteLocationIsNotFoundException();
        }

    }

    @DeleteMapping(value = "/location/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable long id) throws LocationNotFoundException {
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
