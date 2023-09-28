package com.advanced.spring.service;

import com.advanced.spring.core.di.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private Map<String, Student> students = new HashMap<>();

    @Override
    public Student getById(String id) {
        return students.get(id);
    }

    @Override
    public Student add(Student student) {
        students.put(student.getId(), student);
        return student;
    }


    @PostConstruct
    public void init() {
        Student serhii = Student.builder()
                .id("1")
                .name("Serhii")
                .build();

        Student vlad = Student.builder()
                .id("2")
                .name("Vlad")
                .build();
        students.put("1",serhii);
        students.put("2",vlad);
    }
}
