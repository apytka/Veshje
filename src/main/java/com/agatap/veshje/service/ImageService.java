package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateImageDTO;
import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.controller.DTO.UpdateImageDTO;
import com.agatap.veshje.controller.mapper.ImageDTOMapper;
import com.agatap.veshje.model.Image;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.repository.ImageRepository;
import com.agatap.veshje.service.exception.ImageAlreadyExistsException;
import com.agatap.veshje.service.exception.ImageDataInvalidException;
import com.agatap.veshje.service.exception.ImageNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;
    private ImageDTOMapper mapper;
    private ProductService productService;

    public List<ImageDTO> getListImages() {
        return imageRepository.findAll().stream()
                .map(images -> mapper.mappingToDTO(images))
                .collect(Collectors.toList());
    }

    public ImageDTO findImageDTOById(Integer id) throws ImageNotFoundException {
        return imageRepository.findById(id)
                .map(image -> mapper.mappingToDTO(image))
                .orElseThrow(() -> new ImageNotFoundException());
    }

    public Image findImageById(Integer id) throws ImageNotFoundException {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException());
    }

    public ImageDTO createImage(MultipartFile file, CreateImageDTO createUpdateImageDTO) throws IOException, ImageDataInvalidException, ImageAlreadyExistsException, ProductNotFoundException {
        if (imageRepository.existsByName(file.getOriginalFilename())) {
            throw new ImageAlreadyExistsException();
        }
        if (file.getSize() > 16777216 || file.getOriginalFilename().contains("..")) {
            throw new ImageDataInvalidException();
        }
        Image image = mapper.mappingToModel(file);
        image.setCreateDate(OffsetDateTime.now());

        if(createUpdateImageDTO.getProductId() != null) {
            Product product = productService.findProductById(createUpdateImageDTO.getProductId());
            image.setProduct(product);
            product.getImages().add(image);
        }

        Image newImage = imageRepository.save(image);
        return mapper.mappingToDTO(newImage);
    }

    public ImageDTO updateImage(UpdateImageDTO updateImageDTO, Integer id)
            throws ImageNotFoundException, ProductNotFoundException {
        Image image = findImageById(id);
        image.setName(updateImageDTO.getName());
        image.setUpdateDate(OffsetDateTime.now());

        if(updateImageDTO.getProductId() != null) {
            Product product = productService.findProductById(updateImageDTO.getProductId());
            image.setProduct(product);
            product.getImages().add(image);
        }

        Image updateImage = imageRepository.save(image);
        return mapper.mappingToDTO(updateImage);
    }

    @Transactional
    public void deleteImage(Integer id) throws ImageNotFoundException {
        Image image = findImageById(id);
        imageRepository.delete(image);
    }

    public ResponseEntity<byte[]> downloadImage(Integer id) throws ImageDataInvalidException {
        Optional<Image> fileOptional = imageRepository.findById(id);

        if (fileOptional.isPresent()) {
            Image image = fileOptional.get();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                    .body(image.getData());
        } else {
            throw new ImageDataInvalidException();
        }
    }
}