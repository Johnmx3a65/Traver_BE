package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.exception.impl.PhotoNotFoundException;
import com.parovsky.traver.service.PhotoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class PhotoServiceImpl implements PhotoService {

    private final PhotoDAO photoDAO;

    private final LocationDAO locationDAO;

    private final ModelMapper modelMapper;

    @Override
    public List<PhotoDTO> getPhotos(@NonNull Long locationId) throws LocationNotFoundException {
        Location location = locationDAO.get(locationId).orElseThrow(LocationNotFoundException::new);
        List<Photo> photos = photoDAO.getAllByLocation(location);
        return photos.stream().map(photo -> modelMapper.map(photo, PhotoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PhotoDTO addLocationPhoto(@NonNull PhotoDTO photoDTO) throws LocationNotFoundException {
        Location location = locationDAO.get(photoDTO.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Photo photo = Photo.builder()
                .previewUrl(photoDTO.getPreviewUrl())
                .fullUrl(photoDTO.getFullUrl())
                .location(location)
                .build();
        Photo result = photoDAO.save(photo);
        return modelMapper.map(result, PhotoDTO.class);
    }

    @Override
    public PhotoDTO updatePhoto(@NonNull PhotoDTO photoDTO) throws PhotoNotFoundException {
        Photo photo = photoDAO.get(photoDTO.getId()).orElseThrow(PhotoNotFoundException::new);
        photo.setPreviewUrl(photoDTO.getPreviewUrl());
        photo.setFullUrl(photoDTO.getFullUrl());
        Photo result = photoDAO.save(photo);
        return modelMapper.map(result, PhotoDTO.class);
    }

    @Override
    public void deletePhoto(@NonNull Long id) throws PhotoNotFoundException {
        Photo photo = photoDAO.get(id).orElseThrow(PhotoNotFoundException::new);
        photoDAO.delete(photo);
    }
}
