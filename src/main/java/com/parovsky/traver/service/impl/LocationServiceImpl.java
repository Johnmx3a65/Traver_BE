package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
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

    private final PhotoDAO photoDAO;

    @Autowired
    public LocationServiceImpl(LocationDAO locationDAO, CategoryDAO categoryDAO, PhotoDAO photoDAO) {
        this.locationDAO = locationDAO;
        this.categoryDAO = categoryDAO;
        this.photoDAO = photoDAO;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationDAO.getAllLocations();
        return locations.stream().map(LocationService::transformLocationToLocationDTO).collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long id) throws LocationNotFoundException {
        Location location = locationDAO.getLocationById(id);
        return LocationService.transformLocationToLocationDTO(location);
    }

    @Override
    public List<String> getPhotos(Long id) {
        return photoDAO.findAllByLocationId(id).stream().map(Photo::getPhoto).collect(Collectors.toList());
    }

    public LocationDTO saveLocation(@NonNull LocationDTO locationDTO) throws CategoryNotFoundException {
        Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
        return LocationService.transformLocationToLocationDTO(locationDAO.saveLocation(locationDTO, category));
    }

    @Override
    public LocationDTO updateLocation(@NonNull LocationDTO locationDTO) throws LocationNotFoundException, CategoryNotFoundException {
        Category category = categoryDAO.getCategoryById(locationDTO.getCategoryId());
        return LocationService.transformLocationToLocationDTO(locationDAO.updateLocation(locationDTO, category));
    }

    @Override
    public void deleteLocation(@NonNull Long id) throws LocationNotFoundException {
        locationDAO.deleteLocation(id);
    }
}
