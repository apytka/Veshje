package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.service.ReviewService;
import com.agatap.veshje.service.exception.ProductNotFoundException;
import com.agatap.veshje.service.exception.ReviewDataInvalidException;
import com.agatap.veshje.service.exception.ReviewNotFoundException;
import com.agatap.veshje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAllStores() {
        return reviewService.getAllReview();
    }

    @GetMapping("/{id}")
    public ReviewDTO findStoreDTOById(@PathVariable Integer id) throws ReviewNotFoundException {
        return reviewService.findReviewDTOById(id);
    }

    @PostMapping
    public ReviewDTO createStoreDTO(@RequestBody CreateUpdateReviewDTO createReviewDTO) throws ReviewDataInvalidException, UserNotFoundException, ProductNotFoundException {
        return reviewService.createReviewDTO(createReviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateStoreDTO(@RequestBody CreateUpdateReviewDTO updateReviewDTO, @PathVariable Integer id) throws ReviewNotFoundException, ReviewDataInvalidException {
        return reviewService.updateReviewDTO(updateReviewDTO, id);
    }

    @DeleteMapping("/{id}")
    public ReviewDTO deleteStoreDTO(@PathVariable Integer id) throws ReviewNotFoundException {
        return reviewService.deleteReviewDTO(id);
    }

    @GetMapping("/average/{id}")
    public double rateAverage(@PathVariable Integer id) throws ProductNotFoundException {
        return reviewService.rateAverage(id);
    }

    @GetMapping("/length-average/{id}")
    public double rateLengthAverage(@PathVariable Integer id) throws ProductNotFoundException {
        return reviewService.rateLengthAverage(id);
    }

    @GetMapping("/size-average/{id}")
    public double rateSizeAverage(@PathVariable Integer id) throws ProductNotFoundException {
        return reviewService.rateSizeAverage(id);
    }
}
