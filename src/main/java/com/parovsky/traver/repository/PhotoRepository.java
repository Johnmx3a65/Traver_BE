package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

	List<Photo> findAllByLocationId(Long id);
}
