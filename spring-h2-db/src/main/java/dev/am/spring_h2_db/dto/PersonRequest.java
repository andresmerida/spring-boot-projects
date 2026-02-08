package dev.am.spring_h2_db.dto;

import dev.am.spring_h2_db.domain.enums.MaritalStatus;
import dev.am.spring_h2_db.web.custom_validations.DateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        @DateFormat
        String birthDate,

        String email) {

        /**
         * Parses the birthdate from a string into a LocalDate object using the "yyyy-MM-dd" format.
         *
         * @return the birthdate as a LocalDate instance.
         */
        public LocalDate getBirthDate() {
            return LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
}
