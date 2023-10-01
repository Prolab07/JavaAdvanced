package com.advanced.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "advanced_robot_dreams")
@Component
@Scope("prototype")
public class Flora implements Serializable{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String email;
        private String name;
        private String password;
        @ManyToMany
        @JoinTable(
                name = "flora_roles", schema = "advanced_robot_dreams",
                joinColumns = {
                        @JoinColumn(
                                name = "flora_id",
                                nullable = false
                        )
                },
                inverseJoinColumns = {
                        @JoinColumn(
                                name = "flora_id",
                                nullable = false
                        )
                }

        )
        @JsonIgnore
        private List<Role> roleList = new ArrayList<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "Fruit.id")
        private Department department;
    }

