package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhotoDAOImpl implements PhotoDAO {

	private final PhotoRepository photoRepository;

	@Autowired
	public PhotoDAOImpl(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}

	@Override
	public List<Photo> findAllByLocationId(Long id) {
		return photoRepository.findAllByLocationId(id);
	}

	@Override
	public boolean isPhotoExist(Long id) {
		return photoRepository.existsById(id);
	}

	public Photo addLocationPhoto(PhotoDTO photoDTO, Location location) {
		Photo photo = Photo.builder()
				.previewUrl(photoDTO.getPreviewUrl())
				.fullUrl(photoDTO.getFullUrl())
				.location(location)
				.build();
		return photoRepository.save(photo);
	}

	@Override
	public Photo updatePhoto(@NonNull PhotoDTO photoDTO) {
		Photo photo = photoRepository.getById(photoDTO.getId());
		photo.setPreviewUrl(photoDTO.getPreviewUrl());
		photo.setFullUrl(photoDTO.getFullUrl());
		return photoRepository.saveAndFlush(photo);
	}

	@Override
	public void deletePhoto(@NonNull Long id) {
		photoRepository.deleteById(id);
	}
}
