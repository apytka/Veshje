package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateFavouritesDTO;
import com.agatap.veshje.controller.DTO.FavouritesDTO;
import com.agatap.veshje.model.Favourites;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FavouritesDTOMapper {

    public FavouritesDTO mappingToDTO (Favourites favourites) {
        List<String> productsId = favourites.getProducts().stream()
                .map(product -> product.getId())
                .collect(Collectors.toList());
        Integer userId = Optional.ofNullable(favourites.getUser())
                .map(user -> user.getId()).orElse(null);
        return FavouritesDTO.builder()
                .id(favourites.getId())
                .createDate(favourites.getCreateDate())
                .updateDate(favourites.getUpdateDate())
                .productsId(productsId)
                .userId(userId)
                .build();
    }

    public Favourites mappingToModel (CreateUpdateFavouritesDTO createUpdateFavouritesDTO) {
        return Favourites.builder()
                .build();
    }
}
