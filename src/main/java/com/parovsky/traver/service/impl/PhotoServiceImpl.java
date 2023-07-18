package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.model.SavePhotoModel;
import com.parovsky.traver.dto.model.UpdatePhotoModel;
import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.PhotoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class PhotoServiceImpl implements PhotoService {

    private final PhotoDAO photoDAO;

    private final LocationDAO locationDAO;

    private final ModelMapper modelMapper;

    @Override
    public List<PhotoView> getPhotos(@NonNull Long locationId) throws EntityNotFoundException {
        Location location = locationDAO.get(locationId).orElseThrow(() -> new EntityNotFoundException("Location not found"));
        List<Photo> photos = photoDAO.getAllByLocation(location);
        return photos.stream().map(photo -> modelMapper.map(photo, PhotoView.class)).collect(Collectors.toList());
    }

    @Override
    public PhotoView addLocationPhoto(@Valid @NonNull SavePhotoModel model) throws EntityNotFoundException {
        Location location = locationDAO.get(model.getLocationId()).orElseThrow(() -> new EntityNotFoundException("Location not found"));
        Photo photo = Photo.builder()
                .previewUrl(model.getPreviewUrl())
                .fullUrl(model.getFullUrl())
                .location(location)
                .build();
        Photo result = photoDAO.save(photo);
        return modelMapper.map(result, PhotoView.class);
    }

    @Override
    public PhotoView updatePhoto(@Valid @NonNull UpdatePhotoModel model) throws EntityNotFoundException {
        Photo photo = photoDAO.get(model.getId()).orElseThrow(() -> new EntityNotFoundException("Photo not found"));
        photo.setPreviewUrl(model.getPreviewUrl());
        photo.setFullUrl(model.getFullUrl());
        Photo result = photoDAO.save(photo);
        return modelMapper.map(result, PhotoView.class);
    }

    @Override
    public void deletePhoto(@NonNull Long id) throws EntityNotFoundException {
        Photo photo = photoDAO.get(id).orElseThrow(() -> new EntityNotFoundException("Photo not found"));
        photoDAO.delete(photo);
    }
}
