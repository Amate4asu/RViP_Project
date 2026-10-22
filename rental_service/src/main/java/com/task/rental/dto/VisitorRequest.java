package com.task.rental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VisitorRequest(
        @NotBlank
        @Size(max = 200)
        String fullName,

        @NotBlank
        @Size(max = 30)
        String phone,

        @Email
        @Size(max = 254)
        String email
) {
}
