package com.parovsky.traver.dao;

import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;

import java.util.List;

public interface LocationDAO extends Dao<Location> {

	List<Location> getAllByCategoryId(Long categoryId);

	boolean isFavouriteExist(String email, Long locationId);

	List<Location> getFavouritesByUser(User user);

	List<Location> getFavouritesByUserAndCategory(User user, Category category);

	void addFavourite(String email, Long locationId);

	void deleteFavourite(String email, Long locationId);
}
