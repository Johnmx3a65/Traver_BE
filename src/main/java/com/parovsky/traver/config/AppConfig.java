package com.parovsky.traver.config;

import com.parovsky.traver.dto.view.PhotoView;
import com.parovsky.traver.entity.Photo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper
				.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

		modelMapper
				.typeMap(Photo.class, PhotoView.class)
				.addMapping(src -> src.getLocation().getId(), PhotoView::setLocationId);

		return modelMapper;
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoderBean() { return new BCryptPasswordEncoder(); }
}
