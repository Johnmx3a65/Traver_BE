package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

	@Query(value = "SELECT id, category_name, category_picture" +
			" FROM categories" +
			" WHERE categories.id IN (" +
			" SELECT locations.category_id" +
			" FROM locations" +
			" WHERE locations.id IN (" +
			" SELECT users_favourite_locations.location_id" +
			" FROM users_favourite_locations" +
			" WHERE users_favourite_locations.user_id = ?1));", nativeQuery = true)
	List<Category> findFovoriteCategories(Long userId);

}
