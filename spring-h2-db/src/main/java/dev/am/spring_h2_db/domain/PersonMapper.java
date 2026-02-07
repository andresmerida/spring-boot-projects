package dev.am.spring_h2_db.domain;

import dev.am.spring_h2_db.dto.PersonRequest;
import dev.am.spring_h2_db.dto.PersonResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonResponse toDTOResponse(PersonEntity personEntity) {
        return new PersonResponse(
                personEntity.getId(),
                personEntity.getNames(),
                personEntity.getLastName(),
                personEntity.getMaritalStatus(),
                personEntity.getBirthDate(),
                personEntity.getEmail()
        );
    }

    public PersonEntity toEntity(PersonRequest request) {
        return new PersonEntity(
                request.name(),
                request.lastName(),
                request.maritalStatus(),
                request.birthDate(),
                request.email()
        );
    }
}
