package com.parovsky.traver.dao;

import com.parovsky.traver.dto.LocationDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LocationDAO {
    List<Location> getAllLocations();

    Location getLocationById(Long id) throws LocationNotFoundException;

    boolean isLocationExist(Long id);

    boolean isFavouriteLocationExist(Long userId, Long locationId);

    Location saveLocation(@NonNull LocationDTO locationDTO, @NonNull Category category);

    Location updateLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) throws LocationNotFoundException;

    void deleteLocation(Long id) throws LocationNotFoundException;

    List<Location> getFavouriteLocationsByUserId(Long id);

    void addFavouriteLocation(Long userId, Long locationId);

    void deleteFavouriteLocation(Long userId, Long locationId);

     static Location transformLocationDTO(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setName(locationDTO.getName());
        location.setSubtitle(locationDTO.getSubtitle());
        location.setDescription(locationDTO.getDescription());
        location.setCoordinates(locationDTO.getCoordinates());
        location.setPicture(locationDTO.getPicture());
        return location;
    }
}
