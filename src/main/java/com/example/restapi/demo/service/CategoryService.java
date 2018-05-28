package com.example.restapi.demo.service;

import com.example.restapi.demo.model.Category;
import com.example.restapi.demo.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category update(Long id, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        Category storedCategory = optionalCategory.get();
        BeanUtils.copyProperties(category, storedCategory, "id");
        return categoryRepository.save(storedCategory);
    }

}
