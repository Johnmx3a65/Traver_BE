package com.parovsky.traver.service;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.exception.impl.PhotoNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PhotoService {
    List<PhotoDTO> getPhotos(@NonNull Long locationId) throws LocationNotFoundException;

    PhotoDTO addLocationPhoto(@NonNull PhotoDTO photoDTO) throws LocationNotFoundException;

    PhotoDTO updatePhoto(@NonNull PhotoDTO photoDTO) throws LocationNotFoundException, PhotoNotFoundException;

    void deletePhoto(@NonNull Long id) throws PhotoNotFoundException;
}
