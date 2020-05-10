package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.controller.mapper.ReviewDTOMapper;
import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.Review;
import com.agatap.veshje.model.User;
import com.agatap.veshje.repository.ReviewRepository;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.ReviewDataInvalidException;
import com.agatap.veshje.service.exception.ReviewNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ReviewDTOMapper mapper;
    private UserService userService;
    private ProductService productService;

    public List<ReviewDTO> getAllReview() {
        return reviewRepository.findAll().stream()
                .map(review -> mapper.mappingToDTO(review))
                .collect(Collectors.toList());
    }

    public ReviewDTO findReviewDTOById(Integer id) throws ReviewNotFoundException {
        return reviewRepository.findById(id)
                .map(review -> mapper.mappingToDTO(review))
                .orElseThrow(() -> new ReviewNotFoundException());
    }

    public Review findReviewById(Integer id) throws ReviewNotFoundException {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException());
    }

    public ReviewDTO createReviewDTO(CreateUpdateReviewDTO createReviewDTO) throws ReviewDataInvalidException, UserNotFoundException, ProductNotFoundException {
        if (createReviewDTO.getRate() == null || createReviewDTO.getRate() < 0 || createReviewDTO.getRate() > 4) {
            throw new ReviewDataInvalidException();
        }
        Review review = mapper.mappingToModel(createReviewDTO);
        review.setCreateDate(OffsetDateTime.now());

        if (createReviewDTO.getUserId() != null) {
            User user = userService.findUserById(createReviewDTO.getUserId());
            user.getReviews().add(review);
            review.setUser(user);
        }
        if (createReviewDTO.getProductId() != null) {
            Product product = productService.findProductById(createReviewDTO.getProductId());
            product.getReviews().add(review);
            review.setProduct(product);
        }
        Review newReview = reviewRepository.save(review);
        return mapper.mappingToDTO(newReview);
    }

    public ReviewDTO updateReviewDTO(CreateUpdateReviewDTO updateReviewDTO, Integer id) throws ReviewNotFoundException, ReviewDataInvalidException {
        if (updateReviewDTO.getRate() == null || updateReviewDTO.getRate() < 0 || updateReviewDTO.getRate() > 4) {
            throw new ReviewDataInvalidException();
        }
        Review review = findReviewById(id);
        review.setComment(updateReviewDTO.getComment());
        review.setRate(updateReviewDTO.getRate());
        review.setRateSize(updateReviewDTO.getRateSize());
        review.setRateLength(updateReviewDTO.getRateLength());
        review.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Review updateReview = reviewRepository.save(review);
        return mapper.mappingToDTO(updateReview);
    }

    public ReviewDTO deleteReviewDTO(Integer id) throws ReviewNotFoundException {
        Review review = findReviewById(id);
        reviewRepository.delete(review);
        return mapper.mappingToDTO(review);
    }

    public double rateAverage(String id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        double average = 0;
        double sum = 0;
        int currency = 1;
        List<Review> reviews = product.getReviews();
        for (Review review : reviews) {
            sum += review.getRate();
            average = sum / currency;
            currency++;
        }
        return average;
    }

    public double rateLengthAverage(String id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        double lengthAverage = 0;
        double sum = 0;
        int currency = 1;
        List<Review> reviews = product.getReviews();
        for (Review review : reviews) {
            sum += review.getRateLength();
            lengthAverage = sum / currency;
            currency++;
        }
        return lengthAverage;
    }

    public double rateSizeAverage(String id) throws ProductNotFoundException {
        Product product = productService.findProductById(id);
        double sizeAverage = 0;
        double sum = 0;
        int currency = 1;
        List<Review> reviews = product.getReviews();
        for (Review review : reviews) {
            sum += review.getRateSize();
            sizeAverage = sum / currency;
            currency++;
        }
        return sizeAverage;
    }
}
