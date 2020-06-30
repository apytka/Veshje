package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.*;
import com.agatap.veshje.controller.mapper.ShoppingCartDTOMapper;
import com.agatap.veshje.model.ShoppingCart;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.repository.CouponCodeRepository;
import com.agatap.veshje.service.exception.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
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
    @Autowired
    private CouponCodeRepository couponCodeRepository;
    @Autowired
    private CouponCodeService couponCodeService;
    @Autowired
    private DeliveryService deliveryService;

    public List<ShoppingCartDTO> getAllProductsInCart() {
        return products.stream()
                .map(shoppingCart -> mapper.mappingToDTO(shoppingCart))
                .collect(Collectors.toList());
    }

    public ShoppingCartDTO addProductToShoppingCart(CreateUpdateShoppingCartDTO createUpdateShoppingCartDTO) throws ProductNotFoundException, UnsupportedEncodingException, SizeNotFoundException, CouponCodeNotFoundException {
        ShoppingCart shoppingCart = mapper.mappingToModel(createUpdateShoppingCartDTO);
        ShoppingCart shoppingCartFilter = filterByProductIdAndSizeType(createUpdateShoppingCartDTO.getProductId(), createUpdateShoppingCartDTO.getSizeType());
        if (shoppingCartFilter == null) {
            List<ImageDTO> imageByProductId = productService.findImageByProductId(createUpdateShoppingCartDTO.getProductId());
            ImageDTO imageDTO = imageByProductId.get(0);
            byte[] encodeBase64 = Base64.encodeBase64(imageDTO.getData());
            String base64Encoded = new String(encodeBase64, "UTF-8");

            shoppingCart.setId(++idCounter);
            shoppingCart.setProductId(createUpdateShoppingCartDTO.getProductId());
            shoppingCart.setProductName(productService.findProductById(createUpdateShoppingCartDTO.getProductId()).getName());
            shoppingCart.setProductColor(productService.findProductById(createUpdateShoppingCartDTO.getProductId()).getColor());
            shoppingCart.setProductPrice(productService.findProductById(createUpdateShoppingCartDTO.getProductId()).getPrice());
            shoppingCart.setProductImage(base64Encoded);
            shoppingCart.setQuantity(createUpdateShoppingCartDTO.getQuantity());
            shoppingCart.setSizeType(createUpdateShoppingCartDTO.getSizeType());
            shoppingCart.setQuantityInStock(sizeService.getQuantityBySizeTypeAndProductId(createUpdateShoppingCartDTO.getSizeType(), createUpdateShoppingCartDTO.getProductId()));
            if (!products.isEmpty()) {
                String couponCode = products.get(0).getCouponCode();
                if (couponCode != null) {
                    CouponCodeDTO couponCodeDTOByCode = couponCodeService.findCouponCodeDTOByCode(products.get(0).getCouponCode());
                    shoppingCart.setCouponCode(couponCode);
                    shoppingCart.setProductSalePrice(shoppingCart.getProductPrice() * (1 - couponCodeDTOByCode.getPercentDiscount() / 100));
                }
            }
            products.add(shoppingCart);
        } else {
            shoppingCartFilter.setQuantity(shoppingCartFilter.getQuantity() + 1);
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

    public void checkoutStock(ShoppingCart shoppingCart) throws ProductNotFoundException, SizeNotFoundException, NotEnoughProductsInStockException {
        for (ShoppingCart product : products) {
            if (sizeService.getQuantityBySizeTypeAndProductId(shoppingCart.getSizeType(), shoppingCart.getProductId())
                    < product.getQuantity()) {
                throw new NotEnoughProductsInStockException();
            }
        }
    }

    public ShoppingCartDTO filterByProductIdAndSizeTypeDTO(String id, SizeType sizeType) {
        List<ShoppingCartDTO> productInShoppingCart = findProductInShoppingCart(id);
        return productInShoppingCart.stream()
                .filter(size -> size.getSizeType().equals(sizeType))
                .findAny()
                .orElse(null);
    }

    public ShoppingCart filterByProductIdAndSizeType(String id, SizeType sizeType) {
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
        for (ShoppingCartDTO shoppingCartDTO : allProductsInCart) {
            totalQuantity += shoppingCartDTO.getQuantity();
        }
        return totalQuantity;
    }


    public List<ShoppingCartDTO> addCouponCodeToShoppingCart(ChangeCouponCodeDTO changeCouponCodeDTO)
            throws CouponCodeNotFoundException, CouponCodeInvalidDataException {

        CouponCodeDTO couponCode = couponCodeService.findCouponCodeDTOByCode(changeCouponCodeDTO.getCouponCode());
        if (!couponCodeRepository.existsByCode(changeCouponCodeDTO.getCouponCode().toLowerCase())
                || products.isEmpty()
                || OffsetDateTime.now().isBefore(couponCode.getStartDiscount())
                || OffsetDateTime.now().isAfter(couponCode.getExpireDiscount())) {
            throw new CouponCodeInvalidDataException();
        } else {
            CouponCodeDTO couponCodeDTOByCode = couponCodeService.findCouponCodeDTOByCode(changeCouponCodeDTO.getCouponCode().toLowerCase());
            for (ShoppingCart product : products) {
                product.setCouponCode(changeCouponCodeDTO.getCouponCode().toLowerCase());
                product.setProductSalePrice(product.getProductPrice() * (1 - couponCodeDTOByCode.getPercentDiscount() / 100));
            }
        }
        return products.stream()
                .map(shoppingCart -> mapper.mappingToDTO(shoppingCart))
                .collect(Collectors.toList());
    }

    public List<ShoppingCartDTO> removeCouponCodeWithShoppingCart() {
        for (ShoppingCart shoppingCart : products) {
            shoppingCart.setCouponCode(null);
            shoppingCart.setProductSalePrice(null);
        }
        return products.stream()
                .map(shoppingCart -> mapper.mappingToDTO(shoppingCart))
                .collect(Collectors.toList());
    }

    public Double getTotalPrice() throws ProductNotFoundException {
        Double totalAmount = 0.0;
        for (ShoppingCart product : products) {
            totalAmount += (product.getProductPrice() * product.getQuantity());
        }
        return totalAmount;
    }

    public Double getTotalSalePrice() {
        Double totalAmount = 0.0;
        if (!products.isEmpty()) {
            if (products.get(0).getCouponCode() != null) {
                for (ShoppingCart product : products) {
                    totalAmount += (product.getProductSalePrice() * product.getQuantity());
                }
            }
        }
        return totalAmount;
    }

    public Double getMinDeliveryPrice() throws ProductNotFoundException {
        Double priceDelivery;
        if (getTotalSalePrice() != 0 && getTotalSalePrice() >= 100) {
            priceDelivery = 0.0;
        } else if (getTotalPrice() >= 100 && getTotalSalePrice() == 0) {
            priceDelivery = 0.0;
        } else {
            priceDelivery = deliveryService.findMinPriceDeliveryDTO().getPrice().doubleValue();
        }
        return priceDelivery;
    }

    public Double checkDeliveryPrice(Integer id) throws ProductNotFoundException, DeliveryNotFoundException {
        Double priceDelivery;
        if (getTotalSalePrice() != 0 && getTotalSalePrice() >= 100) {
            priceDelivery = 0.0;
        } else if (getTotalPrice() >= 100 && getTotalSalePrice() == 0) {
            priceDelivery = 0.0;
        } else {
            priceDelivery = deliveryService.findPriceDeliveryById(id);
        }
        return priceDelivery;
    }

    public Double getTotalPriceWithMinDelivery() throws ProductNotFoundException {
        return getTotalPrice() + getMinDeliveryPrice();
    }

    public Double getTotalSalePriceWithMinDelivery() throws ProductNotFoundException {
        return getTotalSalePrice() + getMinDeliveryPrice();
    }

    public Double getTotalDiscount() throws ProductNotFoundException {
        return getTotalPriceWithMinDelivery() - getTotalSalePriceWithMinDelivery();
    }

    public Double getTotalPriceWithDelivery(Integer id) throws ProductNotFoundException, DeliveryNotFoundException {
        return getTotalPrice() + checkDeliveryPrice(id);
    }

    public Double getTotalSalePriceWithDelivery(Integer id) throws DeliveryNotFoundException, ProductNotFoundException {
        return getTotalSalePrice() + checkDeliveryPrice(id);
    }
}
