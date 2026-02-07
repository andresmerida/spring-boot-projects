package dev.am.spring_h2_db.domain;

import dev.am.spring_h2_db.dto.PersonRequest;
import dev.am.spring_h2_db.dto.PersonResponse;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<PersonResponse> findAll();
    Optional<PersonResponse> findById(Integer id);
    PersonResponse save(PersonRequest request);
    PersonResponse edit(Integer id, PersonRequest request);
    void deleteById(Integer id);
}
