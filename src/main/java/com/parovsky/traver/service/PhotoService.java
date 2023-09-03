package com.parovsky.traver.service;

import com.parovsky.traver.dto.form.SavePhotoForm;
import com.parovsky.traver.dto.form.UpdatePhotoForm;
import com.parovsky.traver.dto.view.PhotoView;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface PhotoService {
    List<PhotoView> getPhotos(@NonNull Long locationId);

    PhotoView addLocationPhoto(@NonNull SavePhotoForm model);

    PhotoView updatePhoto(@Valid @NonNull UpdatePhotoForm model);

    void deletePhoto(@NonNull Long id);
}
