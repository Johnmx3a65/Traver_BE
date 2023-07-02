package com.parovsky.traver.dao;

import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;

import java.util.List;

public interface PhotoDAO extends Dao<Photo> {
	List<Photo> getAllByLocation(Location location);
}
