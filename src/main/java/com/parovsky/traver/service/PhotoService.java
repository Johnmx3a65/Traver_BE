package com.parovsky.traver.service;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PhotoService {
    List<PhotoDTO> getPhotos(@NonNull Long locationId) throws EntityNotFoundException;

    PhotoDTO addLocationPhoto(@NonNull PhotoDTO photoDTO) throws EntityNotFoundException;

    PhotoDTO updatePhoto(@NonNull PhotoDTO photoDTO) throws EntityNotFoundException;

    void deletePhoto(@NonNull Long id) throws EntityNotFoundException;
}
