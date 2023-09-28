package com.advanced.spring.core.di;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter

public class Fruit {
    private String id;
    private String name;
    private String email;

}