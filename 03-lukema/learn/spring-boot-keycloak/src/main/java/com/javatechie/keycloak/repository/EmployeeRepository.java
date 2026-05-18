package com.javatechie.keycloak.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.keycloak.entity.Employee;


public interface EmployeeRepository
    extends JpaRepository<Employee, Integer> {
}
