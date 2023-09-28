package com.advanced.spring.core.di;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/*
    1. Inversion of Control (IoC) та Dependency Injection (DI)
    Що таке IoC?
    IoC - це принцип, за яким об'єкти не створюють залежності самостійно, а отримують їх з зовнішнього джерела.

    Що таке DI?
    DI - це конкретна реалізація IoC, де залежності ін'єктуються в об'єкт через конструктор, сеттери або анотації.

    AOP - Aspect Oriented Programming
 */
@Component
//@Lazy  //ініціалізація після того як десь - коли буде виклик методів цього біна
public class Car {
    /*
        У цьому прикладі Car має залежність від Engine. Замість того, щоб створювати Engine в середині класу Car,
        ми ін'єктуємо його через конструктор.
     */
    private final Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    @PostConstruct
    public void initCar() {
        System.out.println("PostConstruct in Car");
    }

    @PreDestroy
    public void destroyCar() {
        System.out.println("PreDestroy in Car");
    }

    public void start() {
        System.out.println("Car method start");
        engine.turnOn();
        System.out.println("Car method finished");
    }
}
