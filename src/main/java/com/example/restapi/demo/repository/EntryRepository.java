package com.example.restapi.demo.repository;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.entry.EntryRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryQuery {

}
