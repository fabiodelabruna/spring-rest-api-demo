package com.example.restapi.demo.repository.entry;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.filter.EntryFilter;

import java.util.List;

public interface EntryRepositoryQuery {

    List<Entry> search(EntryFilter filter);

}
