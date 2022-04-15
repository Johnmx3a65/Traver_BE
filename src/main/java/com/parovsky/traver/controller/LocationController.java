package com.parovsky.traver.controller;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDTO>> getLocations() {
        List<LocationDTO> locations = locationService.getAllLocations();
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

    @PutMapping(value = "/location", consumes = "application/json")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
        LocationDTO location = locationService.updateLocation(locationDTO);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @DeleteMapping(value = "/location/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable long id) throws LocationNotFoundException {
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
