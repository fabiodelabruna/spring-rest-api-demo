package com.example.restapi.demo.resource;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entry")
public class EntryResource {

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Entry> entry = entryRepository.findById(id);
        return entry.isPresent() ? ResponseEntity.ok(entry.get()) : ResponseEntity.notFound().build();
    }

}
