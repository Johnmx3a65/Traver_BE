package com.parovsky.traver.service;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.parovsky.traver.service.LocationServiceTest.LocationServiceTestContextConfiguration.locationDAO;
import static com.parovsky.traver.service.LocationServiceTest.LocationServiceTestContextConfiguration.photoDAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class LocationServiceTest {

	private final LocationService subject;

	@Autowired
	public LocationServiceTest(LocationService locationService) {
		this.subject = locationService;
	}

	@TestConfiguration
	static class LocationServiceTestContextConfiguration {

		public static final LocationDAO locationDAO = mock(LocationDAO.class);

		public static final CategoryDAO categoryDAO = mock(CategoryDAO.class);

		public static final PhotoDAO photoDAO = mock(PhotoDAO.class);

		public static final UserDAO userDAO = mock(UserDAO.class);

		@Bean
		public LocationDAO locationDAO() {
			return locationDAO;
		}

		@Bean
		public CategoryDAO categoryDAO() {
			return categoryDAO;
		}

		@Bean
		public PhotoDAO photoDAO() {
			return photoDAO;
		}

		@Bean
		public UserDAO userDAO() {
			return userDAO;
		}
	}

	@BeforeEach
	void setUp() {

	}

	@Test
	void testAddLocationPhoto() throws LocationNotFoundException {
		Location location = new Location();
		location.setId(1L);
		Photo photo = Photo
				.builder()
				.id(1L)
				.url("photo-url")
				.location(location)
				.build();
		PhotoDTO photoDTO = PhotoDTO
				.builder()
				.id(1L)
				.url("photo-url")
				.build();
		PhotoView expected = PhotoView
				.builder()
				.id(1L)
				.url("photo-url")
				.locationId(1L)
				.build();

		doReturn(location).when(locationDAO).getLocationById(1L);
		doReturn(photo).when(photoDAO).addLocationPhoto(photoDTO, location);

		PhotoView actual = subject.addLocationPhoto(photoDTO, 1L);

		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getUrl(), actual.getUrl());
		assertEquals(expected.getLocationId(), actual.getLocationId());
	}
}