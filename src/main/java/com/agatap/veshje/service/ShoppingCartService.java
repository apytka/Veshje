package com.agatap.veshje.service;

import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.service.exception.NotEnoughProductsInStockException;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.SizeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCartService {
    @Autowired
    private SizeService sizeService;

    private Set<ShoppingCart> products = new HashSet<>();

    public void addProductToShoppingCart(ShoppingCart shoppingCart) {
        for(ShoppingCart product : products) {
            if(product.getProduct().equals(shoppingCart.getProduct())) {
                product.setQuantity(shoppingCart.getQuantity() + 1);
            } else {
                products.add(product);
            }
        }
    }

    public void removeProductWithShoppingCart(ShoppingCart shoppingCart) {
        for(ShoppingCart product : products) {
            if(product.getProduct().equals(shoppingCart.getProduct())) {
                if(product.getQuantity() > 1) {
                    product.setQuantity(product.getQuantity() - 1);
                } else if (product.getQuantity() == 1) {
                    products.remove(product);
                }
            }
        }
    }

    public Double getTotal() {
        Double totalAmount = null;
        for(ShoppingCart product : products) {
            totalAmount += product.getProduct().getPrice() * product.getQuantity();
        }
        return totalAmount;
    }

    public void checkoutStock(ShoppingCart shoppingCart) throws ProductNotFoundException, SizeNotFoundException, NotEnoughProductsInStockException {
        for(ShoppingCart product : products) {
            if(sizeService.getQuantityBySizeTypeAndProductId(shoppingCart.getSizeType(), shoppingCart.getProduct().getId())
                    < product.getQuantity()) {
                throw new NotEnoughProductsInStockException();
            }
        }
    }
}
