package com.task.rental.dto;

import java.time.LocalDateTime;

public record VisitorResponse(
        Long id,
        String fullName,
        String phone,
        String email,
        LocalDateTime registeredAt,
        LocalDateTime terminatedAt,
        boolean hasDisk,
        boolean active
) {
}