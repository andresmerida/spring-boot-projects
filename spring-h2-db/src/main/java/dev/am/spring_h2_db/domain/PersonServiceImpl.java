package dev.am.spring_h2_db.domain;

import dev.am.spring_h2_db.dto.PersonRequest;
import dev.am.spring_h2_db.dto.PersonResponse;
import dev.am.spring_h2_db.web.exceptions.PersonNotFoundException;
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
        return personMapper.toDTOResponse(
                personRepository.findById(id)
                        .map(personEntity -> {
                            personEntity.setEmail(request.email());
                            personEntity.setMaritalStatus(request.maritalStatus());
                            personEntity.setBirthDate(request.getBirthDate());
                            personEntity.setLastName(request.lastName());
                            personEntity.setNames(request.name());
                            return personRepository.save(personEntity);
                        })
                .orElseThrow(() -> PersonNotFoundException.of(id))
        );
    }

    @Override
    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }
}
