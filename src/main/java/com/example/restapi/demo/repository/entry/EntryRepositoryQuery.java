package com.example.restapi.demo.repository.entry;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.filter.EntryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryRepositoryQuery {

    Page<Entry> search(EntryFilter filter, Pageable pageable);

}
