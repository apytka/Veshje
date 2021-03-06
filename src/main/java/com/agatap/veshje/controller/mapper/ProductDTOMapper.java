package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateProductDTO;
import com.agatap.veshje.controller.DTO.ProductDTO;
import com.agatap.veshje.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOMapper {
    public ProductDTO mappingToDTO(Product product) {
        List<Integer> ordersId = product.getOrders().stream()
                .map(order -> order.getId())
                .collect(Collectors.toList());
        List<Integer> compositionsId = product.getComposition().stream()
                .map(composition -> composition.getId())
                .collect(Collectors.toList());
        List<Integer> categoriesId = product.getCategories().stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        List<Integer> reviewsId = product.getReviews().stream()
                .map(review -> review.getId())
                .collect(Collectors.toList());
        List<Integer> careProductsId = product.getCares().stream()
                .map(cares-> cares.getId())
                .collect(Collectors.toList());
        List<Integer> imagesId = product.getImages().stream()
                .map(image -> image.getId())
                .collect(Collectors.toList());
        List<Integer> sizesId = product.getSizes().stream()
                .map(size -> size.getId())
                .collect(Collectors.toList());
        List<Integer> favouritesId = product.getFavourites().stream()
                .map(favourites -> favourites.getId())
                .collect(Collectors.toList());
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .color(product.getColor())
                .description(product.getDescription())
                .complementDescription(product.getComplementDescription())
                .orderIds(ordersId)
                .compositionIds(compositionsId)
                .categoriesIds(categoriesId)
                .reviewIds(reviewsId)
                .careIds(careProductsId)
                .imagesIds(imagesId)
                .sizeIds(sizesId)
                .favouritesIds(favouritesId)
                .createDate(product.getCreateDate())
                .updateDate(product.getUpdateDate())
                .build();
    }

    public Product mappingToModel(CreateUpdateProductDTO createProductDTO) {
        return Product.builder()
                .name(createProductDTO.getName())
                .price(createProductDTO.getPrice())
                .color(createProductDTO.getColor())
                .description(createProductDTO.getDescription())
                .complementDescription(createProductDTO.getComplementDescription())
                .build();
    }
}
