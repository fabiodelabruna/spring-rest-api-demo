package com.example.restapi.demo.resource;

import com.example.restapi.demo.event.CreatedResourceEvent;
import com.example.restapi.demo.exceptionhandler.ApiExceptionHandler;
import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.EntryRepository;
import com.example.restapi.demo.repository.filter.EntryFilter;
import com.example.restapi.demo.service.EntryService;
import com.example.restapi.demo.service.exception.NonExistentOrInactivePersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entry")
public class EntryResource {

    @Autowired
    private EntryService entryService;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public Page<Entry> search(EntryFilter filter, Pageable pageable) {
        return entryRepository.search(filter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Entry> entry = entryRepository.findById(id);
        return entry.isPresent() ? ResponseEntity.ok(entry.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        entryRepository.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<Entry> create(@Valid @RequestBody Entry entry, HttpServletResponse response) {
        Entry storedEntry = entryService.save(entry);
        publisher.publishEvent(new CreatedResourceEvent(this, response, storedEntry.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(storedEntry);
    }

    @ExceptionHandler({NonExistentOrInactivePersonException.class})
    public ResponseEntity<Object> handleNonExistentOrInactivePersonException(RuntimeException ex) {
        String errorMessage = messageSource.getMessage("person.non-existent-or-inactive", null, LocaleContextHolder.getLocale());
        String detailMessage = ex.toString();
        List<ApiExceptionHandler.Error> errors = Arrays.asList(new ApiExceptionHandler.Error(errorMessage, detailMessage));
        return ResponseEntity.badRequest().body(errors);
    }

}
