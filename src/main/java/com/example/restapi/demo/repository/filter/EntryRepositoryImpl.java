package com.example.restapi.demo.repository.filter;

import com.example.restapi.demo.model.Entry;
import com.example.restapi.demo.repository.entry.EntryRepositoryQuery;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Entry> search(EntryFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Entry> criteria = builder.createQuery(Entry.class);
        Root<Entry> root = criteria.from(Entry.class);

        Predicate[] predicates = createRestictions(filter, builder, root);
        criteria.where(predicates);

        TypedQuery<Entry> query = manager.createQuery(criteria);
        return query.getResultList();
    }

    private Predicate[] createRestictions(EntryFilter filter, CriteriaBuilder builder, Root<Entry> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(filter.getDescription())) {
            predicates.add(builder.like(
                builder.lower(root.get("description")), "%" + filter.getDescription() + "%"
            ));
        }

        if (filter.getInitialDueDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dueDate"), filter.getInitialDueDate()));
        }

        if (filter.getFinalDueDate() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dueDate"), filter.getFinalDueDate()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
