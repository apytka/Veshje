package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateImageDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.UpdateImageDTO;
import com.agatap.veshje.service.exception.ImageAlreadyExistsException;
import com.agatap.veshje.service.ImageService;
import com.agatap.veshje.service.exception.ImageDataInvalidException;
import com.agatap.veshje.service.exception.ImageNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
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
    public ImageDTO findImageDTOById(@PathVariable Integer id) throws ImageNotFoundException {
        return imageService.findImageDTOById(id);
    }

    @PostMapping
    public ImageDTO createImage(MultipartFile file, CreateImageDTO createUpdateImageDTO)
            throws IOException, ImageDataInvalidException, ImageAlreadyExistsException, ProductNotFoundException {
        return imageService.createImage(file, createUpdateImageDTO);
    }

    @PutMapping("/{id}")
    public ImageDTO updateImageName(@RequestBody UpdateImageDTO updateImageDTO, @PathVariable Integer id)
            throws ImageNotFoundException, ProductNotFoundException {
        return imageService.updateImage(updateImageDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Integer id) throws ImageNotFoundException {
        imageService.deleteImage(id);
    }

    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Integer id) throws ImageDataInvalidException {
        return imageService.downloadImage(id);
    }
}
