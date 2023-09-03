package com.parovsky.traver.service;

import com.parovsky.traver.dto.form.SavePhotoForm;
import com.parovsky.traver.dto.form.UpdatePhotoForm;
import com.parovsky.traver.dto.response.PhotoResponse;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface PhotoService {
    List<PhotoResponse> getPhotos(@NonNull Long locationId);

    PhotoResponse addLocationPhoto(@NonNull SavePhotoForm model);

    PhotoResponse updatePhoto(@Valid @NonNull UpdatePhotoForm model);

    void deletePhoto(@NonNull Long id);
}
