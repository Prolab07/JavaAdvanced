package com.advanced.spring.service;

import com.advanced.spring.core.di.Fruit;

public interface FruitService {
    Fruit getById(String id);

    Fruit add(Fruit fruit);

}
