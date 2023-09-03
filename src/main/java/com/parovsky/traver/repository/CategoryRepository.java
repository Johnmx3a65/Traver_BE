package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findAllByLocationsFollowersEmail(String email);

	boolean existsByName(String name);
}
