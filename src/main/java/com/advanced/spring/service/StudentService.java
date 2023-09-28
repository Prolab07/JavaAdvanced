package com.advanced.spring.service;

import com.advanced.spring.core.di.Student;

public interface StudentService {
    Student getById(String id);

    Student add(Student student);
}
