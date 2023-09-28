package com.advanced.spring.core.beans;

import com.advanced.spring.core.di.Car;
import com.advanced.spring.core.di.Engine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Spring Beans та Application Context
Spring Bean - це об'єкт, який створюється, конфігурується та управляється Spring IoC контейнером.

Application Context - це інтерфейс для доступу до конфігураційних параметрів Spring. Він надає доступ до beans та різних конфігураційних аспектів.
 */

@Configuration
public class BeanConfig {

    /*@Bean
    public Engine engine() {
        return new Engine();
    }*/

   /* @Bean
    @Lazy
    public Car car(Engine engine) {
        return new Car(engine);
    }*/
}
