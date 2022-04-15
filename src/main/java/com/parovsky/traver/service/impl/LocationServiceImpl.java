package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDAO locationDAO;

    private final CategoryDAO categoryDAO;

    @Autowired
    public LocationServiceImpl(LocationDAO locationDAO, CategoryDAO categoryDAO) {
        this.locationDAO = locationDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationDAO.getAllLocations();
        return locations.stream().map(this::transformLocationToLocationDTO).collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long id) throws LocationNotFoundException {
        Location location = locationDAO.getLocationById(id);
        return transformLocationToLocationDTO(location);
    }

    public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException {
        Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
        return transformLocationToLocationDTO(locationDAO.saveLocation(locationDTO, category));
    }

    @Override
    public LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
        Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
        return transformLocationToLocationDTO(locationDAO.updateLocation(locationDTO, category));
    }

    @Override
    public void deleteLocation(@NonNull Long id) throws LocationNotFoundException {
        locationDAO.deleteLocation(id);
    }

    private LocationDTO transformLocationToLocationDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getName(),
                location.getDescription(),
                location.getCoordinates(),
                location.getCategory().getId()
        );
    }
}
