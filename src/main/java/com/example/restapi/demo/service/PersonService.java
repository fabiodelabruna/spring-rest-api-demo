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
        Person storedPerson = findPersonById(id);
        BeanUtils.copyProperties(person, storedPerson, "id");
        return personRepository.save(storedPerson);
    }

    public void updateActiveProperty(Long id, Boolean active) {
        Person storedPerson = findPersonById(id);
        storedPerson.setActive(active);
        personRepository.save(storedPerson);
    }

    public Person findPersonById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (!optionalPerson.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return optionalPerson.get();
    }

}
