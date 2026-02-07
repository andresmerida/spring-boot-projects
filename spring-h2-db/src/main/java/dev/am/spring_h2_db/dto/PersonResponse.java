package dev.am.spring_h2_db.dto;

import dev.am.spring_h2_db.domain.enums.MaritalStatus;

import java.time.LocalDate;

public record PersonResponse(int id,
                             String name,
                             String lastName,
                             MaritalStatus maritalStatus,
                             LocalDate birthDate,
                             String email) {
}
