package com.parovsky.traver.controller;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.dto.model.SavePhotoModel;
import com.parovsky.traver.dto.model.UpdatePhotoModel;
import com.parovsky.traver.exception.EntityNotFoundException;
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
    public List<PhotoDTO> getPhotos(@PathVariable Long locationId) throws EntityNotFoundException {
        return photoService.getPhotos(locationId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/photo", consumes = "application/json")
    public PhotoDTO addLocationPhoto(@Valid @RequestBody SavePhotoModel model) throws EntityNotFoundException {
        return photoService.addLocationPhoto(model);
    }

    @ResponseBody
    @PutMapping(value = "/photo", consumes = "application/json")
    public PhotoDTO updatePhoto(@Valid @RequestBody UpdatePhotoModel model) throws EntityNotFoundException {
        return photoService.updatePhoto(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/photo/{id}")
    public void deleteLocation(@PathVariable Long id) throws EntityNotFoundException {
        photoService.deletePhoto(id);
    }
}
