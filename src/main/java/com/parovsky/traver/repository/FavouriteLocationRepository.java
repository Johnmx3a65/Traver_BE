package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Category;
import com.parovsky.traver.entity.FavouriteLocation;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation, FavouriteLocation.FavouriteLocationPK> {
	@Query("SELECT f.location FROM FavouriteLocation f WHERE f.user.email = :email")
	List<Location> findAllLocationsByUserEmail(String email);

	@Query("SELECT f.location FROM FavouriteLocation f WHERE f.user.email = :email and f.location.category.id = :categoryId")
	List<Location> findAllLocationsByUserEmailAndCategoryId(String email, Long categoryId);

	@Query(value = "SELECT CAST(CAST(COUNT (1) as int) as bool) FROM location_user WHERE user_id IN (SELECT id FROM \"user\" WHERE email like ?1) AND location_id = ?2", nativeQuery = true)
	boolean existsByUserEmailAndLocationId(String email, Long locationId);

	@Modifying
	@Query(value = "INSERT INTO location_user (user_id, location_id) VALUES ((SELECT id FROM \"user\" WHERE email like ?1), ?2)", nativeQuery = true)
	void save(String email, Long locationId);

	@Modifying
	@Query(value = "DELETE FROM location_user WHERE user_id IN (SELECT id FROM \"user\" WHERE email like ?1) AND location_id = ?2", nativeQuery = true)
	void delete(String email, Long locationId);

	List<FavouriteLocation> findAllByUser(User user);

	List<FavouriteLocation> findAllByUserAndLocationCategory(User user, Category category);
}

