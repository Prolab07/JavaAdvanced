package com.advanced.spring.core.di;

import org.springframework.stereotype.Component;

@Component
public class Engine {
    public void turnOn() {
        System.out.println("Engine is turned on!");
    }
}
