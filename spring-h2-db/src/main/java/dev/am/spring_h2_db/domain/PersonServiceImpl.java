package dev.am.spring_h2_db.domain;

import dev.am.spring_h2_db.dto.PersonRequest;
import dev.am.spring_h2_db.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> findAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toDTOResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonResponse> findById(Integer id) {
        return personRepository.findById(id)
                .map(personMapper::toDTOResponse);
    }

    @Override
    public PersonResponse save(PersonRequest request) {
        return personMapper.toDTOResponse(personRepository.save(personMapper.toEntity(request)));
    }

    @Override
    public PersonResponse edit(Integer id, PersonRequest request) {
        PersonEntity personEntity = personRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Person not found for id: " + id)
        );
        personEntity.setEmail(request.email());
        personEntity.setMaritalStatus(request.maritalStatus());
        personEntity.setBirthDate(request.birthDate());
        personEntity.setLastName(request.lastName());
        personEntity.setNames(request.name());

        return personMapper.toDTOResponse(personRepository.save(personEntity));
    }

    @Override
    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }
}
