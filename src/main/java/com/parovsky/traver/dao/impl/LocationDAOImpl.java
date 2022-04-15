package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@Transactional
public class LocationDAOImpl implements LocationDAO {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationDAOImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(@NonNull Long id) throws LocationNotFoundException {
        return locationRepository.findById(id).orElseThrow(LocationNotFoundException::new);
    }

    @Override
    public Location saveLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) {
        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setDescription(locationDTO.getDescription());
        location.setCoordinates(locationDTO.getCoordinates());
        location.setCategory(category);
        locationRepository.saveAndFlush(location);
        return location;
    }

    @Override
    public Location updateLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) throws LocationNotFoundException {
        Location location = locationRepository.findById(locationDTO.getId()).orElseThrow(LocationNotFoundException::new);
        location.setName(locationDTO.getName());
        location.setDescription(locationDTO.getDescription());
        location.setCoordinates(locationDTO.getCoordinates());
        location.setCategory(category);
        locationRepository.saveAndFlush(location);
        return location;
    }

    @Override
    public void deleteLocation(Long id) throws LocationNotFoundException {
        if (locationRepository.deleteAllById(id) == 0) {
            throw new LocationNotFoundException();
        }
    }
}
