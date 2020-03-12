package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.CreateUpdateReviewDTO;
import com.agatap.veshje.controller.DTO.ReviewDTO;
import com.agatap.veshje.service.ReviewService;
import com.agatap.veshje.service.exception.ReviewDataInvalid;
import com.agatap.veshje.service.exception.ReviewNotFoundException;
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
    public ReviewDTO createStoreDTO(@RequestBody CreateUpdateReviewDTO createReviewDTO) throws ReviewDataInvalid {
        return reviewService.createReviewDTO(createReviewDTO);
    }

    @PutMapping("/{id}")
    public ReviewDTO updateStoreDTO(@RequestBody CreateUpdateReviewDTO updateReviewDTO, @PathVariable Integer id) throws ReviewNotFoundException, ReviewDataInvalid {
        return reviewService.updateReviewDTO(updateReviewDTO, id);
    }

    @DeleteMapping("/{id}")
    public ReviewDTO deleteStoreDTO(@PathVariable Integer id) throws ReviewNotFoundException {
        return reviewService.deleteReviewDTO(id);
    }
}
