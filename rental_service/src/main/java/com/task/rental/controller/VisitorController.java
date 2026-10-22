package com.task.rental.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.task.rental.dto.*;
import com.task.rental.service.VisitorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitorResponse create(
            @Valid @RequestBody VisitorRequest request
    ) {
        return visitorService.create(request);
    }

    @GetMapping
    public List<VisitorResponse> findAll() {
        return visitorService.findAll();
    }

    @GetMapping("/{id}")
    public VisitorResponse findById(@PathVariable Long id) {
        return visitorService.findById(id);
    }

    @PutMapping("/{id}")
    public VisitorResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VisitorRequest request
    ) {
        return visitorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public VisitorResponse terminate(@PathVariable Long id) {
        return visitorService.terminate(id);
    }

    @PostMapping("/{id}/disk")
    public VisitorResponse issueDisk(@PathVariable Long id) {
        return visitorService.issueDisk(id);
    }

    @DeleteMapping("/{id}/disk")
    public VisitorResponse returnDisk(@PathVariable Long id) {
        return visitorService.returnDisk(id);
    }

    @GetMapping("/report")
    public VisitorReportResponse getReport() {
        return visitorService.getReport();
    }
}