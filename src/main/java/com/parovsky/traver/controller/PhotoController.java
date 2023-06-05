package com.parovsky.traver.controller;

import com.parovsky.traver.dto.PhotoDTO;
import com.parovsky.traver.exception.impl.LocationNotFoundException;
import com.parovsky.traver.exception.impl.PhotoNotFoundException;
import com.parovsky.traver.service.PhotoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class PhotoController {

    private final PhotoService photoService;

    @ResponseBody
    @GetMapping("/photos/{locationId}")
    public List<PhotoDTO> getPhotos(@PathVariable Long locationId) throws LocationNotFoundException {
        return photoService.getPhotos(locationId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/photo", consumes = "application/json")
    public PhotoDTO addLocationPhoto(@RequestBody PhotoDTO photoDTO) throws LocationNotFoundException {
        return photoService.addLocationPhoto(photoDTO);
    }

    @ResponseBody
    @PutMapping(value = "/photo", consumes = "application/json")
    public PhotoDTO updatePhoto(@RequestBody PhotoDTO photoDTO) throws LocationNotFoundException, PhotoNotFoundException {
        return photoService.updatePhoto(photoDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/photo/{id}")
    public void deleteLocation(@PathVariable Long id) throws PhotoNotFoundException {
        photoService.deletePhoto(id);
    }
}
