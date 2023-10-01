package com.advanced.spring.service;

import com.advanced.spring.core.di.Fruit;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FruitServiceImplements implements FruitService {
    private Map<String, Fruit> fruits = new HashMap<>();

    @Override
    public Fruit getById(String id) {
        return fruits.get(id);
    }

    @Override
    public Fruit add(Fruit fruit) {
        fruits.put(fruit.getId(), fruit);
        return fruit;
    }

    @PostConstruct
    public void init() {
        Fruit banana = Fruit.builder().build()
                .id("1")
                .name("banana")
                .email()
                .build();


        Fruit apple = Fruit.builder().build()
                .id("2")
                .name("apple")
                .email()
                .build();

        fruits.put("1", banana);
        fruits.put("2", apple);


    }
}
