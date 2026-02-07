package dev.am.spring_h2_db.web;

import dev.am.spring_h2_db.domain.PersonService;
import dev.am.spring_h2_db.dto.PersonRequest;
import dev.am.spring_h2_db.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
class PersonController {
    private final PersonService personService;

    @GetMapping
    ResponseEntity<List<PersonResponse>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<PersonResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findById(id).orElseThrow(
                () -> new RuntimeException("Person not found for id: " + id)
        ));
    }

    @PostMapping
    ResponseEntity<PersonResponse> save(@RequestBody PersonRequest request) throws URISyntaxException {
        PersonResponse response = personService.save(request);
        return ResponseEntity
                .created(new URI("/persons/" + response.id()))
                .body(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<PersonResponse> edit(@PathVariable Integer id, @RequestBody PersonRequest request) {
        return ResponseEntity.ok(personService.edit(id, request));
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        personService.deleteById(id);
    }
}
