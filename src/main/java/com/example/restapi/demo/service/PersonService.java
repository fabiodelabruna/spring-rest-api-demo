package com.example.restapi.demo.service;

import com.example.restapi.demo.model.Person;
import com.example.restapi.demo.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person update(Long id, Person person) {
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (!optionalPerson.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        Person storedPerson = optionalPerson.get();
        BeanUtils.copyProperties(person, storedPerson, "id");
        return personRepository.save(storedPerson);
    }

}
