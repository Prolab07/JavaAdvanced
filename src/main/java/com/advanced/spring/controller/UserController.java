package com.advanced.spring.controller;

import com.advanced.spring.core.di.Student;
import com.advanced.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private final StudentService service;

    @Autowired
    public UserController(StudentService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAdminInfo() {
        return "Hello admin";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/info")
    public String getUser() {
        return "Hello user";
    }

    @GetMapping("/public")
    public String publicInfo() {
        return "Hello and here is a public info";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/parameter/{studentId}")
    public ResponseEntity<Student> getParameter(@PathVariable("studentId") String studentId){
        return ResponseEntity.ok(service.getById(studentId));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/requestParameter")
    public ResponseEntity<Student> getByRequestParam(@RequestParam("studentId") String studentId){
        return ResponseEntity.ok(service.getById(studentId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/requestBody")
    public ResponseEntity<Student> postStudent(@RequestBody Student student){
        return ResponseEntity.ok(service.add(student));
    }
}
