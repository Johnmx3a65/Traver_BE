package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);

	@Query(value = "SELECT id, category_name, picture" +
			" FROM category" +
			" WHERE category.id IN (" +
			" SELECT location.category_id" +
			" FROM location" +
			" WHERE location.id IN (" +
			" SELECT location_user.location_id" +
			" FROM location_user" +
			" WHERE location_user.user_id IN (" +
			" SELECT id" +
			" FROM \"user\"" +
			" WHERE email like ?1)));", nativeQuery = true)
	List<Category> findFavouriteCategories(String email);

}
