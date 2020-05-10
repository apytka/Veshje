package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.FavouritesDTOMapper;
import com.agatap.veshje.model.Favourites;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.User;
import com.agatap.veshje.repository.FavouritesRepository;
import com.agatap.veshje.service.exception.FavouritesNotFoundException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavouritesService {

    private FavouritesRepository favouritesRepository;
    private FavouritesDTOMapper mapper;
    private ProductService productService;
    private UserService userService;

    public List<FavouritesDTO> getAllFavourites() {
        return favouritesRepository.findAll().stream()
                .map(favourites -> mapper.mappingToDTO(favourites))
                .collect(Collectors.toList());
    }

    public FavouritesDTO findFavouritesDTOById(Integer id) throws FavouritesNotFoundException {
        return favouritesRepository.findById(id)
                .map(favourites -> mapper.mappingToDTO(favourites))
                .orElseThrow(() -> new FavouritesNotFoundException());
    }

    public Favourites findFavouritesById(Integer id) throws FavouritesNotFoundException {
        return favouritesRepository.findById(id)
                .orElseThrow(() -> new FavouritesNotFoundException());
    }

    public FavouritesDTO createFavouritesDTO(CreateUpdateFavouritesDTO createUpdateFavouritesDTO, Integer userId) throws ProductNotFoundException, UserNotFoundException {
        Favourites favourites = mapper.mappingToModel(createUpdateFavouritesDTO);
        favourites.setCreateDate(OffsetDateTime.now());

        List<String> products = createUpdateFavouritesDTO.getProductsId();
        if (products == null) {
            favourites.setProducts(new ArrayList<>());
        } else {
            for (String product : products) {
                Product productById = productService.findProductById(product);
                favourites.getProducts().add(productById);
                productById.getFavourites().add(favourites);
            }
        }

        User user = userService.findUserById(userId);
        user.setFavourites(favourites);
        favourites.setUser(user);

        Favourites newFavourites = favouritesRepository.save(favourites);
        return mapper.mappingToDTO(newFavourites);
    }

    public FavouritesDTO updateFavouritesDTO(CreateUpdateFavouritesDTO createUpdateFavouritesDTO, Integer id)
            throws UserNotFoundException, FavouritesNotFoundException, ProductNotFoundException {
        Favourites favourites = findFavouritesById(id);

        favourites.setUpdateDate(OffsetDateTime.now());

        List<String> products = createUpdateFavouritesDTO.getProductsId();
        for (String product : products) {
            Product productById = productService.findProductById(product);
            favourites.getProducts().add(productById);
            productById.getFavourites().add(favourites);
        }
        if (createUpdateFavouritesDTO.getUserId() != null) {
            User user = userService.findUserById(createUpdateFavouritesDTO.getUserId());
            user.setFavourites(favourites);
            favourites.setUser(user);
        }

        return mapper.mappingToDTO(favourites);
    }

    @Transactional
    public FavouritesDTO deleteFavouritesDTO(Integer id) throws FavouritesNotFoundException {
        Favourites favourites = findFavouritesById(id);
        favouritesRepository.delete(favourites);
        return mapper.mappingToDTO(favourites);
    }

    public FavouritesDTO findFavouritesDTOByUserId(Integer id) throws FavouritesNotFoundException {
//        User user = userService.findUserById(id);
//        return mapper.mappingToDTO(user.getFavourites());
        return favouritesRepository.findByUserId(id)
                .map(favourites -> mapper.mappingToDTO(favourites))
                .orElseThrow(() -> new FavouritesNotFoundException());
    }

    public Favourites findFavouritesByUserId(Integer id) throws FavouritesNotFoundException {
        return favouritesRepository.findByUserId(id)
                .orElseThrow(() -> new FavouritesNotFoundException());
    }

}
