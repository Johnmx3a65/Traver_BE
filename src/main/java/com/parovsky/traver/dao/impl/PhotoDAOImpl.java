package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class PhotoDAOImpl implements PhotoDAO {

	private final PhotoRepository photoRepository;

	@Override
	public Optional<Photo> get(@NonNull Long id) {
		return photoRepository.findById(id);
	}

	@Override
	public List<Photo> getAll() {
		return photoRepository.findAll();
	}

	@Override
	public List<Photo> getAllByLocation(Location location) {
		return photoRepository.findAllByLocation(location);
	}

	@Override
	public Photo save(@NonNull Photo photo) {
		return photoRepository.saveAndFlush(photo);
	}

	@Override
	public void delete(@NonNull Photo photo) {
		photoRepository.delete(photo);
	}
}
