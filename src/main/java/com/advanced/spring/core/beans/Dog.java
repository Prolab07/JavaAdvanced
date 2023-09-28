package com.advanced.spring.core.beans;

import org.springframework.stereotype.Service;

@Service("dog2")
public class Dog implements Animal {
    @Override
    public void voice() {
        System.out.println("gav");
    }
}
