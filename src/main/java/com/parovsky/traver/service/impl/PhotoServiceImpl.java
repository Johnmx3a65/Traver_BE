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
        if (!locationDAO.isLocationExist(locationId)) {
            throw new LocationNotFoundException();
        }
        List<Photo> photos = photoDAO.findAllByLocationId(locationId);
        return photos.stream().map(photo -> modelMapper.map(photo, PhotoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PhotoDTO addLocationPhoto(@NonNull PhotoDTO photoDTO) throws LocationNotFoundException {
        Location location = locationDAO.getLocationById(photoDTO.getLocationId());
        if (location == null) {
            throw new LocationNotFoundException();
        }
        Photo photo = photoDAO.addLocationPhoto(photoDTO, location);
        return modelMapper.map(photo, PhotoDTO.class);
    }

    @Override
    public PhotoDTO updatePhoto(@NonNull PhotoDTO photoDTO) throws PhotoNotFoundException {
        if (!photoDAO.isPhotoExist(photoDTO.getId())) {
            throw new PhotoNotFoundException();
        }
        Photo result = photoDAO.updatePhoto(photoDTO);
        return modelMapper.map(result, PhotoDTO.class);
    }

    @Override
    public void deletePhoto(@NonNull Long id) throws PhotoNotFoundException {
        if (!photoDAO.isPhotoExist(id)) {
            throw new PhotoNotFoundException();
        }
        photoDAO.deletePhoto(id);
    }
}
