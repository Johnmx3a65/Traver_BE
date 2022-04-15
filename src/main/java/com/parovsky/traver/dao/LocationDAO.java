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

    Location saveLocation(@NonNull LocationDTO locationDTO, @NonNull Category category);

    Location updateLocation(@NonNull LocationDTO locationDTO, @NonNull Category category) throws LocationNotFoundException;

    void deleteLocation(Long id) throws LocationNotFoundException;
}
