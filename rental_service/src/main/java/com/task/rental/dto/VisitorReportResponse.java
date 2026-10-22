package com.task.rental.dto;

public record VisitorReportResponse(
        long totalVisitors,
        long visitorsWithDisk
) {
}