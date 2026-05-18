package com.javatechie.keycloak.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.keycloak.entity.Employee;
import com.javatechie.keycloak.repository.EmployeeRepository;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void initializeEmployeeTable() {
        // @formatter:off
        employeeRepository.saveAll(
                Stream.of(new Employee("john", 20000),
                          new Employee("mak", 55000),
                          new Employee("peter", 120000))
                .collect(Collectors.toList()));
        // @formatter:on
    }

    public Employee getEmployee(int employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
