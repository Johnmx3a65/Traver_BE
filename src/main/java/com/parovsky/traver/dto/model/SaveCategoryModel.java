package com.parovsky.traver.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@Builder
@AllArgsConstructor
public class SaveCategoryModel {
	private String name;

	private String picture;
}
