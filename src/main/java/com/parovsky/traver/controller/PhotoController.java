package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.SavePhotoForm;
import com.parovsky.traver.dto.form.UpdatePhotoForm;
import com.parovsky.traver.dto.response.PhotoResponse;
import com.parovsky.traver.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class PhotoController {

    private final PhotoService photoService;

    @ResponseBody
    @GetMapping("/photos/{locationId}")
    public List<PhotoResponse> getPhotos(@PathVariable Long locationId) {
        return photoService.getPhotos(locationId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/photo", consumes = "application/json")
    public PhotoResponse addLocationPhoto(@Valid @RequestBody SavePhotoForm model) {
        return photoService.addLocationPhoto(model);
    }

    @ResponseBody
    @PutMapping(value = "/photo", consumes = "application/json")
    public PhotoResponse updatePhoto(@Valid @RequestBody UpdatePhotoForm model) {
        return photoService.updatePhoto(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/photo/{id}")
    public void deleteLocation(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }
}
