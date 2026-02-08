package dev.am.spring_h2_db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.am.spring_h2_db.domain.enums.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotBlank(message = "Lastname is required")
        @Size(min = 2, max = 100, message = "Lastname must be between 2 and 100 characters")
        String lastName,

        @NotNull(message = "Marital status is required")
        MaritalStatus maritalStatus,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        String email) {
}
