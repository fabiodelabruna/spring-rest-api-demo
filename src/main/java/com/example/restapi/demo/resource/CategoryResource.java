package com.example.restapi.demo.resource;

import com.example.restapi.demo.event.CreatedResourceEvent;
import com.example.restapi.demo.model.Category;
import com.example.restapi.demo.repository.CategoryRepository;
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
@RequestMapping("/category")
public class CategoryResource {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.isPresent() ? ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category, HttpServletResponse response) {
        Category storedCategory = categoryRepository.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, storedCategory.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(storedCategory);
    }

}
