package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SavePhotoModel;
import com.parovsky.traver.dto.model.UpdatePhotoModel;
import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface PhotoService {
    List<PhotoView> getPhotos(@NonNull Long locationId) throws EntityNotFoundException;

    PhotoView addLocationPhoto(@NonNull SavePhotoModel model) throws EntityNotFoundException;

    PhotoView updatePhoto(@Valid @NonNull UpdatePhotoModel model) throws EntityNotFoundException;

    void deletePhoto(@NonNull Long id) throws EntityNotFoundException;
}
