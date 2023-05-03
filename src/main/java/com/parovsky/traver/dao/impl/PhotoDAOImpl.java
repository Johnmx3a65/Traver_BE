package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Photo addLocationPhoto(PhotoDTO photoDTO, Location location) {
		Photo photo = Photo.builder()
				.url(photoDTO.getUrl())
				.location(location)
				.build();
		return photoRepository.save(photo);
	}
}
