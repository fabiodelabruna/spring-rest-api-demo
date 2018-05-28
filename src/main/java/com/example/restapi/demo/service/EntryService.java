package com.example.restapi.demo.service;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.model.Person;
import com.example.restapi.demo.repository.EntryRepository;
import com.example.restapi.demo.repository.PersonRepository;
import com.example.restapi.demo.service.exception.NonExistentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private PersonRepository personRepository;

    public Entry save(Entry entry) {
        Optional<Person> person = personRepository.findById(entry.getPerson().getId());

        if (!person.isPresent() || person.get().isInactive()) {
            throw new NonExistentOrInactivePersonException();
        }

        return entryRepository.save(entry);
    }

}
