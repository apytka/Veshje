package com.agatap.veshje.controller;

import com.agatap.veshje.service.exception.ImageAlreadyExistsException;
import com.agatap.veshje.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerConfig {

    @ExceptionHandler(UserDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleUserBadRequest() {
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public void handleUserNotFoundException() {
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserAlreadyExist() {
    }

    @ExceptionHandler(StoreDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleStoreBadRequest() {
    }

    @ExceptionHandler(StoreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleStoreNotFoundException() {
    }

    @ExceptionHandler(ReviewDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleReviewBadRequest() {
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleReviewNotFoundException() {
    }

    @ExceptionHandler(ProductDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleProductBadRequest() {
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleProductNotFoundException() {
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleProductAlreadyExist() {
    }

    @ExceptionHandler(DimensionDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDimensionBadRequest() {
    }

    @ExceptionHandler(DimensionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDimensionNotFoundException() {
    }

    @ExceptionHandler(DimensionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDimensionAlreadyExists() {
    }

    @ExceptionHandler(SizeDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleSizeBadRequest() {
    }

    @ExceptionHandler(SizeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleSizeNotFoundException() {
    }

    @ExceptionHandler(PaymentsTypeDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlePaymentsTypeBadRequest() {
    }

    @ExceptionHandler(PaymentsTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePaymentsTypeNotFoundException() {
    }

    @ExceptionHandler(PaymentsTypeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handlePaymentsTypeAlreadyExists() {
    }

    @ExceptionHandler(PaymentsDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlePaymentsBadRequest() {
    }

    @ExceptionHandler(PaymentsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePaymentsNotFoundException() {
    }

    @ExceptionHandler(OrdersDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleOrdersBadRequest() {
    }

    @ExceptionHandler(OrdersNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleOrdersNotFoundException() {
    }

    @ExceptionHandler(NewsletterDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleNewsletterBadRequest() {
    }

    @ExceptionHandler(NewsletterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNewsletterNotFoundException() {
    }

    @ExceptionHandler(NewsletterAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleNewsletterAlreadyExists() {
    }

    @ExceptionHandler(DeliveryDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDeliveryBadRequest() {
    }

    @ExceptionHandler(DeliveryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDeliveryNotFoundException() {
    }

    @ExceptionHandler(DeliveryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDeliveryAlreadyExists() {
    }

    @ExceptionHandler(CountryDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCountryBadRequest() {
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCountryNotFoundException() {
    }

    @ExceptionHandler(CountryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCountryAlreadyExists() {
    }

    @ExceptionHandler(CompositionProductDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCompositionProductBadRequest() {
    }

    @ExceptionHandler(CompositionProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCompositionProductNotFoundException() {
    }

    @ExceptionHandler(CityDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCityBadRequest() {
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCityNotFoundException() {
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCityAlreadyExists() {
    }

    @ExceptionHandler(CategoryDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCategoryBadRequest() {
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCategoryNotFoundException() {
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCategoryAlreadyExists() {
    }

    @ExceptionHandler(CareProductDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCareProductBadRequest() {
    }

    @ExceptionHandler(CareProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCareProductNotFoundException() {
    }

    @ExceptionHandler(CareProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCareProductAlreadyExists() {
    }

    @ExceptionHandler(AddressDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleAddressBadRequest() {
    }

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleAddressNotFoundException() {
    }

    @ExceptionHandler(ImageDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handlePictureBadRequest() {
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePictureNotFoundException() {
    }

    @ExceptionHandler(ImageAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handlePictureAlreadyExists() {
    }

    @ExceptionHandler(FavouritesDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleFavouritesBadRequest() {
    }

    @ExceptionHandler(FavouritesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleFavouritesNotFoundException() {
    }

    @ExceptionHandler(FavouritesAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleFavouritesAlreadyExists() {
    }

    @ExceptionHandler(OrderItemDataInvalidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleOrderItemDataNotFoundException() {
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleOrderItemAlreadyExists() {
    }

    @ExceptionHandler(NotEnoughProductsInStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleNotEnoughProductsInStock() {
    }

    @ExceptionHandler(ProductInShoppingCartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleProductInShoppingCartNotFoundException() {
    }

    @ExceptionHandler(ShoppingCartDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleShoppingCartNotFoundException() {
    }

    @ExceptionHandler(CouponCodeInvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCouponCodeBadRequest() {
    }

    @ExceptionHandler(CouponCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCouponCodeNotFoundException() {
    }

    @ExceptionHandler(CouponCodeAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCouponCodeAlreadyExist() {
    }

    @ExceptionHandler(ContactDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleContactBadRequest() {
    }

    @ExceptionHandler(ContactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleContactNotFoundException() {
    }

    @ExceptionHandler(ContactAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleContactAlreadyExist() {
    }

    @ExceptionHandler(ContactTopicDataInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleContactTopicBadRequest() {
    }

    @ExceptionHandler(ContactTopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleContactTopicNotFoundException() {
    }

    @ExceptionHandler(ContactTopicAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleContactTopicAlreadyExist() {
    }
}
