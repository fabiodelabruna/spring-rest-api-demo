package com.example.restapi.demo.resource;

import com.example.restapi.demo.event.CreatedResourceEvent;
import com.example.restapi.demo.model.Person;
import com.example.restapi.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonResource {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.isPresent() ? ResponseEntity.ok(person.get()) : ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person storedPerson = personRepository.save(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, storedPerson.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(storedPerson);
    }

}
