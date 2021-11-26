package com.finalproject.peerreview2021.controllers.rest;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UnauthorizedOperationException;
import com.finalproject.peerreview2021.models.Reviewer;
import com.finalproject.peerreview2021.models.dto.ReviewerDto;
import com.finalproject.peerreview2021.services.contracts.ReviewerService;
import com.finalproject.peerreview2021.services.contracts.UserService;
import com.finalproject.peerreview2021.services.modelmappers.ReviewerModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {
    private final ReviewerModelMapper reviewerModelMapper;
    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerModelMapper reviewerModelMapper,
                              ReviewerService reviewerService) {
        this.reviewerModelMapper = reviewerModelMapper;
        this.reviewerService = reviewerService;
    }

    @GetMapping()
    public List<Reviewer> getAll() {
        return reviewerService.getAll();
    }

    @GetMapping("/{id}")
    public Reviewer getById(@PathVariable int id) {
        try {
            return reviewerService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Reviewer create(@Valid @RequestBody ReviewerDto reviewerDto) {
        try {
            Reviewer reviewer = reviewerModelMapper.fromDto(reviewerDto);
            reviewerService.create(reviewer);
            return reviewer;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Reviewer update(@PathVariable int id, @Valid @RequestBody ReviewerDto reviewerDto) {
        try {
            Reviewer reviewer = reviewerModelMapper.fromDto(reviewerDto, id);
            reviewerService.update(reviewer);
            return reviewer;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            reviewerService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
