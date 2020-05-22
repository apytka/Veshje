package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateShoppingCartDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.controller.mapper.ShoppingCartDTOMapper;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartService {
    private List<ShoppingCart> products = new ArrayList<>();
    int idCounter = 0;

    @Autowired
    private SizeService sizeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartDTOMapper mapper;

    @PostConstruct
    public void init() {
        products.add(new ShoppingCart(++idCounter, "YX001-0006", SizeType.S, 1));
        products.add(new ShoppingCart(++idCounter, "YX001-0017", SizeType.S, 1));
        products.add(new ShoppingCart(++idCounter, "YX001-0020", SizeType.XS, 2));
    }

    public List<ShoppingCartDTO> getAllProductsInCart() {
        return products.stream()
                .map(shoppingCart -> mapper.mappingToDTO(shoppingCart))
                .collect(Collectors.toList());

    }

    public ShoppingCartDTO addProductToShoppingCart(CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO) {
        ShoppingCart shoppingCart = mapper.mappingToModel(createUpdateShoppingCartDTO);
        ShoppingCart shoppingCartDTO = filterByProductIdAndSizeType(createUpdateShoppingCartDTO.getProductId(), createUpdateShoppingCartDTO.getSizeType());
        if(shoppingCartDTO == null) {
            shoppingCart.setId(++idCounter);
            shoppingCart.setProductId(createUpdateShoppingCartDTO.getProductId());
            shoppingCart.setQuantity(createUpdateShoppingCartDTO.getQuantity());
            shoppingCart.setSizeType(createUpdateShoppingCartDTO.getSizeType());
            products.add(shoppingCart);
        } else {
            shoppingCartDTO.setQuantity(shoppingCartDTO.getQuantity() + 1);
        }

        return mapper.mappingToDTO(shoppingCart);
    }

    public List<ShoppingCartDTO> findProductInShoppingCart(String id) {
        return products.stream()
                .filter(product -> product.getProductId().equals(id))
                .map(product -> mapper.mappingToDTO(product))
                .collect(Collectors.toList());
    }

    public ShoppingCartDTO findShoppingCartDTOById(Integer id) throws ProductInShoppingCartNotFoundException {
        return products.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(id))
                .map(shoppingCart -> mapper.mappingToDTO(shoppingCart))
                .findAny()
                .orElseThrow(() -> new ProductInShoppingCartNotFoundException());
    }

    public ShoppingCart findShoppingCartById(Integer id) throws ProductInShoppingCartNotFoundException {
        return products.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductInShoppingCartNotFoundException());
    }

    public ShoppingCartDTO removeProductWithShoppingCart(Integer id) throws ProductInShoppingCartNotFoundException {
        ShoppingCart shoppingCart = findShoppingCartById(id);
        products.remove(shoppingCart);
        return mapper.mappingToDTO(shoppingCart);
    }

    public ShoppingCartDTO updateShoppingCartDTO(CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO, Integer id)
            throws ProductInShoppingCartNotFoundException, ShoppingCartDataInvalidException {
        if (createUpdateShoppingCartDTO.getQuantity() < 0) {
            throw new ShoppingCartDataInvalidException();
        }
        ShoppingCart shoppingCart = findShoppingCartById(id);
        shoppingCart.setQuantity(createUpdateShoppingCartDTO.getQuantity());
        shoppingCart.setSizeType(createUpdateShoppingCartDTO.getSizeType());
        return mapper.mappingToDTO(shoppingCart);
    }

    public Double getTotal() throws ProductNotFoundException {
        Double totalAmount = 0.0;
        for (ShoppingCart product : products) {
            Product productById = productService.findProductById(product.getProductId());
            totalAmount += (productById.getPrice() * product.getQuantity());
        }
        return totalAmount;
    }

    public void checkoutStock(ShoppingCart shoppingCart) throws ProductNotFoundException, SizeNotFoundException, NotEnoughProductsInStockException {
        for (ShoppingCart product : products) {
            if (sizeService.getQuantityBySizeTypeAndProductId(shoppingCart.getSizeType(), shoppingCart.getProductId())
                    < product.getQuantity()) {
                throw new NotEnoughProductsInStockException();
            }
        }
    }


    public ShoppingCartDTO filterByProductIdAndSizeTypeDTO (String id, SizeType sizeType) {
        List<ShoppingCartDTO> productInShoppingCart = findProductInShoppingCart(id);
        return productInShoppingCart.stream()
                .filter(size -> size.getSizeType().equals(sizeType))
                .findAny()
                .orElse(null);
    }

    public ShoppingCart filterByProductIdAndSizeType (String id, SizeType sizeType) {
        return products.stream()
                .filter(product -> product.getProductId().equals(id))
                .filter(size -> size.getSizeType().equals(sizeType))
                .findAny()
                .orElse(null);
    }

    public boolean shoppingCartIsEmpty() {
        return products.isEmpty();
    }

    public int quantityProductInShoppingCart() {
        List<ShoppingCartDTO> allProductsInCart = getAllProductsInCart();
        int totalQuantity = 0;
        for(ShoppingCartDTO shoppingCartDTO : allProductsInCart) {
            totalQuantity += shoppingCartDTO.getQuantity();
        }
        return totalQuantity;
    }
}
