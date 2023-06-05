package com.parovsky.traver.dao;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PhotoDAO {

	List<Photo> findAllByLocationId(Long id);

	boolean isPhotoExist(Long id);

	Photo addLocationPhoto(PhotoDTO photoDTO, Location location);

	Photo updatePhoto(@NonNull PhotoDTO photoDTO);

	void deletePhoto(@NonNull Long id);
}
