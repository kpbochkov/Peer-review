package com.finalproject.peerreview2021.controllers.rest;

import com.finalproject.peerreview2021.exceptions.DuplicateEntityException;
import com.finalproject.peerreview2021.exceptions.EntityNotFoundException;
import com.finalproject.peerreview2021.exceptions.UpdateEntityException;
import com.finalproject.peerreview2021.models.WorkItem;
import com.finalproject.peerreview2021.models.dto.WorkItemDto;
import com.finalproject.peerreview2021.services.contracts.WorkItemService;
import com.finalproject.peerreview2021.services.modelmappers.WorkItemModelMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: gog
 * Date: 23.11.2021
 * Time: 12:21
 */
@RestController
@RequestMapping("/api/workitems")
public class WorkItemController {

    private final WorkItemService workItemService;
    private final WorkItemModelMapper workItemModelMapper;


    public WorkItemController(WorkItemService workItemService, WorkItemModelMapper workItemModelMapper) {
        this.workItemService = workItemService;
        this.workItemModelMapper = workItemModelMapper;
    }

    @ApiOperation(value = "Get Work Item by ID")
    @GetMapping("/{id}")
    public WorkItem getById(@PathVariable int id) {
        try {
            return workItemService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ApiOperation(value = "Create new Work Item")
    @PostMapping
    public WorkItem create(@Valid @RequestBody WorkItemDto workItemDto) {
        try {
            WorkItem workItem = workItemModelMapper.fromDto(workItemDto);
            workItemService.create(workItem);
            return workItem;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ApiOperation(value = "Update existing Work Item with the given id")
    @PutMapping("/{id}")
    public WorkItem update(@PathVariable int id, @Valid @RequestBody WorkItemDto workItemDto) {
        try {
            WorkItem workItem = workItemModelMapper.fromDto(workItemDto, id);
            workItemService.update(workItem);
            return workItem;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UpdateEntityException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete Work Item with the given id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            workItemService.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @ApiOperation(value = "Filter Work Item by title, status and reviewer")
    @GetMapping("/filter")
    public List<WorkItem> filter(
            @RequestParam(required = false) Optional<String> title,
            @RequestParam(required = false) Optional<String> status,
            @RequestParam(required = false) Optional<String> reviewer,
            @RequestParam(required = false) Optional<String> sortParam) {
        return workItemService.filter(title, status, reviewer, sortParam);
    }
}
