package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateFavouritesDTO;
import com.agatap.veshje.controller.DTO.FavouritesDTO;
import com.agatap.veshje.model.Favourites;
import com.agatap.veshje.service.FavouritesService;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouritesController {
    @Autowired
    private FavouritesService favouritesService;

    @GetMapping
    public List<FavouritesDTO> getAllFavourites() {
        return favouritesService.getAllFavourites();
    }

    @GetMapping("/{id}")
    public FavouritesDTO findFavouritesDTOById(@PathVariable Integer id) throws FavouritesNotFoundException {
        return favouritesService.findFavouritesDTOById(id);
    }

    @PostMapping
    public FavouritesDTO createFavouritesDTO(@Valid @RequestBody CreateUpdateFavouritesDTO createUpdateFavouritesDTO, Integer id)
            throws ProductNotFoundException, UserNotFoundException {
        return favouritesService.createFavouritesDTO(createUpdateFavouritesDTO, id);
    }

    @PutMapping("/{id}")
    public FavouritesDTO updateFavouritesDTO(@RequestBody CreateUpdateFavouritesDTO createUpdateFavouritesDTO, @PathVariable Integer id)
            throws FavouritesNotFoundException, UserNotFoundException, ProductNotFoundException {
        return favouritesService.updateFavouritesDTO(createUpdateFavouritesDTO, id);
    }

    @DeleteMapping("/{id}")
    public FavouritesDTO deleteFavouritesDTO(@PathVariable Integer id) throws FavouritesNotFoundException {
        return favouritesService.deleteFavouritesDTO(id);
    }

    @GetMapping("/user/{id}")
    public Favourites findFavouritesByUserId(@PathVariable Integer id) throws FavouritesNotFoundException {
        return favouritesService.findFavouritesByUserId(id);
    }

}
