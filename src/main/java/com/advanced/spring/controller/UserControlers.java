package com.advanced.spring.controller;

import com.advanced.spring.core.di.Fruit;
import com.advanced.spring.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public class UserControlers {

    @RestController
    @RequestMapping("/api/v1/users")
    public class UserController {

        private final FruitService service;

        @Autowired
        public UserController(FruitService service) {
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
        @GetMapping("/parameter/{fruitId}")
        public ResponseEntity<Fruit> getParameter(@PathVariable("fruitId") String fruitId){
            return ResponseEntity.ok(service.getById(fruitId));
        }


        @PreAuthorize("hasRole('USER')")
        @GetMapping("/requestParameter")
        public ResponseEntity<Fruit> getByRequestParam(@RequestParam("fruitId") String fruitId){
            return ResponseEntity.ok(service.getById(fruitId));
        }

        @PreAuthorize("hasRole('USER')")
        @PostMapping("/requestBody")
        public ResponseEntity<Fruit> postStudent(@RequestBody Fruit fruit){
            return ResponseEntity.ok(service.add(fruit));
        }
    }

}
