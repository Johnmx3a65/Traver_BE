package com.parovsky.traver.service;

import com.parovsky.traver.dao.LocationDAO;
import com.parovsky.traver.dao.PhotoDAO;
import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.Photo;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.parovsky.traver.service.PhotoServiceTest.PhotoServiceTestContextConfiguration.locationDAO;
import static com.parovsky.traver.service.PhotoServiceTest.PhotoServiceTestContextConfiguration.photoDAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
class PhotoServiceTest {

	private final PhotoService subject;

	@Autowired
	public PhotoServiceTest(PhotoService photoService) {
		this.subject = photoService;
	}

	@TestConfiguration
	static class PhotoServiceTestContextConfiguration {

		public static final LocationDAO locationDAO = mock(LocationDAO.class);

		public static final PhotoDAO photoDAO = mock(PhotoDAO.class);

		@Bean
		public LocationDAO locationDAO() {
			return locationDAO;
		}

		@Bean
		public PhotoDAO photoDAO() {
			return photoDAO;
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
				.previewUrl("photo-url")
				.location(location)
				.build();
		PhotoDTO photoDTO = PhotoDTO
				.builder()
				.id(1L)
				.previewUrl("photo-url")
				.locationId(1L)
				.build();
		PhotoDTO expected = PhotoDTO
				.builder()
				.id(1L)
				.previewUrl("photo-url")
				.locationId(1L)
				.build();

		doReturn(location).when(locationDAO).getLocationById(1L);
		doReturn(photo).when(photoDAO).addLocationPhoto(photoDTO, location);

		PhotoDTO actual = subject.addLocationPhoto(photoDTO);

		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getPreviewUrl(), actual.getPreviewUrl());
		assertEquals(expected.getLocationId(), actual.getLocationId());
	}
}