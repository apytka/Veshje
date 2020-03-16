package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.UpdateImageDTO;
import com.agatap.veshje.service.exception.ImageAlreadyExistsException;
import com.agatap.veshje.service.ImageService;
import com.agatap.veshje.service.exception.ImageDataInvalidException;
import com.agatap.veshje.service.exception.ImageNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<ImageDTO> getAllImages() {
        return imageService.getListImages();
    }

    @GetMapping("/{id}")
    public ImageDTO findImageDTOById(@PathVariable Integer id) throws ImageNotFoundExceptions {
        return imageService.findImageDTOById(id);
    }

    @PostMapping
    public ImageDTO createImage(MultipartFile file) throws IOException, ImageDataInvalidException, ImageAlreadyExistsException {
        return imageService.createImage(file);
    }

    @PutMapping("/{id}")
    public ImageDTO updateImageName(@RequestBody UpdateImageDTO updateImageDTO, @PathVariable Integer id)
            throws ImageNotFoundExceptions {
        return imageService.updateImage(updateImageDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Integer id) throws ImageNotFoundExceptions {
        imageService.deleteImage(id);
    }

    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) throws ImageDataInvalidException {
        return imageService.downloadImage(id);
    }
}
