package com.parovsky.traver.repository;

import com.parovsky.traver.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	List<Location> findAllByCategoryId(Long categoryId);

	List<Location> findAllByFollowersEmail(String email);

	List<Location> findAllByFollowersEmailAndCategoryId(String email, Long categoryId);

	boolean existsByNameAndSubtitle(String name, String subtitle);

	boolean existsByCategoryId(Long categoryId);

	boolean existsByIdAndFollowersEmail(Long id, String email);
}
