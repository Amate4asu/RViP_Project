package com.task.rental.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.task.rental.dto.*;
import com.task.rental.entity.Visitor;
import com.task.rental.repository.VisitorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Transactional
    public VisitorResponse create(VisitorRequest request) {
        Visitor visitor = new Visitor(
                request.fullName().trim(),
                request.phone().trim(),
                request.email(),
                LocalDateTime.now()
        );

        return toResponse(visitorRepository.save(visitor));
    }

    @Transactional(readOnly = true)
    public List<VisitorResponse> findAll() {
        return visitorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public VisitorResponse findById(Long id) {
        return toResponse(getVisitor(id));
    }

    @Transactional
    public VisitorResponse update(
            Long id,
            VisitorRequest request
    ) {
        Visitor visitor = getVisitor(id);
        ensureActive(visitor);

        visitor.setFullName(request.fullName().trim());
        visitor.setPhone(request.phone().trim());
        visitor.setEmail(request.email());

        return toResponse(visitorRepository.save(visitor));
    }

    @Transactional
    public VisitorResponse terminate(Long id) {
        Visitor visitor = getVisitor(id);
        ensureActive(visitor);

        if (visitor.isHasDisk()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Return the disk before terminating the visitor"
            );
        }

        visitor.terminate(LocalDateTime.now());

        return toResponse(visitorRepository.save(visitor));
    }

    @Transactional
    public VisitorResponse issueDisk(Long id) {
        Visitor visitor = getVisitor(id);
        ensureActive(visitor);

        if (visitor.isHasDisk()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The visitor already has a disk"
            );
        }

        visitor.issueDisk();

        return toResponse(visitorRepository.save(visitor));
    }

    @Transactional
    public VisitorResponse returnDisk(Long id) {
        Visitor visitor = getVisitor(id);

        if (!visitor.isHasDisk()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The visitor does not have a disk"
            );
        }

        visitor.returnDisk();

        return toResponse(visitorRepository.save(visitor));
    }

    @Transactional(readOnly = true)
    public VisitorReportResponse getReport() {
        return new VisitorReportResponse(
                visitorRepository.countAllVisitors(),
                visitorRepository.countByHasDiskTrue()
        );
    }

    private Visitor getVisitor(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Visitor not found: " + id
                ));
    }

    private void ensureActive(Visitor visitor) {
        if (!visitor.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "The visitor relationship has already been terminated"
            );
        }
    }

    private VisitorResponse toResponse(Visitor visitor) {
        return new VisitorResponse(
                visitor.getId(),
                visitor.getFullName(),
                visitor.getPhone(),
                visitor.getEmail(),
                visitor.getRegisteredAt(),
                visitor.getTerminatedAt(),
                visitor.isHasDisk(),
                visitor.isActive()
        );
    }
}