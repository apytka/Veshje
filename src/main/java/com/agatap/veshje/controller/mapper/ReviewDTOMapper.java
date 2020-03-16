package com.agatap.veshje.controller.mapper;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.model.Review;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReviewDTOMapper {
    public ReviewDTO mappingToDTO(Review review) {
        Integer productId = Optional.ofNullable(review.getProduct())
                .map(product -> review.getId()).orElse(null);
        Integer userId = Optional.ofNullable(review.getUser())
                .map(user -> review.getId()).orElse(null);
        return ReviewDTO.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rate(review.getRate())
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
                .build();
    }
}
