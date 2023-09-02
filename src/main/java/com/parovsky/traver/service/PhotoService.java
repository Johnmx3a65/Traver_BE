package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SavePhotoModel;
import com.parovsky.traver.dto.model.UpdatePhotoModel;
import com.parovsky.traver.dto.view.PhotoView;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface PhotoService {
    List<PhotoView> getPhotos(@NonNull Long locationId);

    PhotoView addLocationPhoto(@NonNull SavePhotoModel model);

    PhotoView updatePhoto(@Valid @NonNull UpdatePhotoModel model);

    void deletePhoto(@NonNull Long id);
}
