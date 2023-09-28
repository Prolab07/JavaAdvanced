package com.advanced.spring.core.beans;

import org.springframework.stereotype.Service;

@Service
public class Cat implements Animal {
    @Override
    public void voice() {
        System.out.println("may");
    }
}
