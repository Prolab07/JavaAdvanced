package com.advanced.spring.repository;

import com.advanced.spring.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findByNameContaining(String nameFragment);
}
