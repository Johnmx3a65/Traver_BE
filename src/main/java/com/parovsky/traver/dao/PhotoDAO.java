package com.parovsky.traver.dao;

import com.parovsky.traver.entity.Photo;

import java.util.List;

public interface PhotoDAO {

	List<Photo> findAllByLocationId(Long id);
}
