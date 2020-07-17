package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.model.Review;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReviewDTOMapper {
    public ReviewDTO mappingToDTO(Review review) {
        String productId = Optional.ofNullable(review.getProduct())
                .map(product -> product.getId()).orElse(null);
        Integer userId = Optional.ofNullable(review.getUser())
                .map(user -> user.getId()).orElse(null);
        return ReviewDTO.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
                .rateSize(review.getRateSize())
                .rateLength(review.getRateLength())
                .sizeType(review.getSizeType())
                .productIds(productId)
                .userId(userId)
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .build();
    }

    public Review mappingToModel(CreateUpdateReviewDTO createReviewDTO) {
        return Review.builder()
                .comment(createReviewDTO.getComment())
                .rate(createReviewDTO.getRate())
                .rateSize(createReviewDTO.getRateSize())
                .rateLength(createReviewDTO.getRateLength())
                .sizeType(createReviewDTO.getSizeType())
                .build();
    }
}
