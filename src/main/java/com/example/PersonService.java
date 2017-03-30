package com.example;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alejandro Duarte
 */
@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll(int offset, int limit, Map<String, Boolean> sortOrders) {
        System.err.println(String.format("Request: offset %s \t limit%s ", offset, limit));
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());

        PageRequest pageRequest = new PageRequest(page, limit, orders.isEmpty() ? null : new Sort(orders));
        List<Person> items = repository.findAll(pageRequest).getContent();
        return items.subList(offset%limit, items.size());
    }

    public Integer count() {
        return Math.toIntExact(repository.count());
    }

}
