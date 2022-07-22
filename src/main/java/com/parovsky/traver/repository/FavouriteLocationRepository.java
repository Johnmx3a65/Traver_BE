package com.parovsky.traver.repository;

import com.parovsky.traver.entity.FavouriteLocation;
import com.parovsky.traver.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation, FavouriteLocation.FavouriteLocationPK> {
	@Query("SELECT f.location FROM FavouriteLocation f WHERE f.user.id = :userId")
	List<Location> findAllLocationsByUserId(Long userId);

	@Modifying
	@Query(value = "INSERT INTO users_favourite_locations (user_id, location_id) VALUES (?1, ?2)", nativeQuery = true)
	void save(Long userId, Long locationId);

	@Modifying
	@Query(value = "DELETE FROM users_favourite_locations WHERE user_id = ?1 AND location_id = ?2", nativeQuery = true)
	void delete(Long userId, Long locationId);
}

