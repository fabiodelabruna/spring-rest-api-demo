package com.example.restapi.demo.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class EntryFilter {

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate initialDueDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finalDueDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInitialDueDate() {
        return initialDueDate;
    }

    public void setInitialDueDate(LocalDate initialDueDate) {
        this.initialDueDate = initialDueDate;
    }

    public LocalDate getFinalDueDate() {
        return finalDueDate;
    }

    public void setFinalDueDate(LocalDate finalDueDate) {
        this.finalDueDate = finalDueDate;
    }

}
