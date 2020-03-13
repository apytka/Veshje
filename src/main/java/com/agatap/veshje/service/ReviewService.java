package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.controller.mapper.ReviewDTOMapper;
import com.agatap.veshje.model.Review;
import com.agatap.veshje.repository.ReviewRepository;
import com.agatap.veshje.service.exception.ReviewDataInvalidException;
import com.agatap.veshje.service.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewDTOMapper mapper;

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

    public ReviewDTO createReviewDTO(CreateUpdateReviewDTO createReviewDTO) throws ReviewDataInvalidException {
        if(createReviewDTO.getRate() == null || createReviewDTO.getRate() < 0 || createReviewDTO.getRate() > 4) {
            throw new ReviewDataInvalidException();
        }
        Review review = mapper.mappingToModel(createReviewDTO);
        review.setCreateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Review newReview = reviewRepository.save(review);
        return mapper.mappingToDTO(newReview);
    }

    public ReviewDTO updateReviewDTO(CreateUpdateReviewDTO updateReviewDTO, Integer id) throws ReviewNotFoundException, ReviewDataInvalidException {
        if(updateReviewDTO.getRate() == null || updateReviewDTO.getRate() < 0 || updateReviewDTO.getRate() > 4) {
            throw new ReviewDataInvalidException();
        }
        Review review = findReviewById(id);
        review.setComment(updateReviewDTO.getComment());
        review.setRate(updateReviewDTO.getRate());
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
}
