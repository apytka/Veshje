package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.ImageDTO;
import com.agatap.veshje.model.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ImageDTOMapper {

    public ImageDTO mappingToDTO(Image image) {
        Integer productsId = Optional.ofNullable(image.getProduct())
                .map(product -> product.getId()).orElse(null);
        List<Integer> caresId = image.getCares().stream()
                .map(careProduct -> careProduct.getId())
                .collect(Collectors.toList());
        return ImageDTO.builder()
                .id(image.getId())
                .name(image.getName())
                .type(image.getType())
                .size(image.getSize())
                .data(image.getData())
                .productId(productsId)
                .careIds(caresId)
                .createDate(image.getCreateDate())
                .updateDate(image.getUpdateDate())
                .build();
    }

    public Image mappingToModel(MultipartFile file) throws IOException {
        return Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .build();
    }
}
