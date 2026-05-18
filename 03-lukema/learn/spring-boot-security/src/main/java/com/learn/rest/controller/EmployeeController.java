package com.learn.rest.controller;


import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learn.rest.dao.EmployeeDAO;
import com.learn.rest.model.Employee;
import com.learn.rest.model.Employees;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping(path = "/spring")
public class EmployeeController {
    @Autowired
    private EmployeeDAO employeeDao;

    @GetMapping("/ping")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public String ping(Principal principal, Authentication authentication) {

        log.info(() -> "Called. ping()");

        if (principal == null) {
            log.info(() -> "principal is null");
        } else {
            log.info(() -> "principal.getName(): " + principal.getName());
        }

        if (authentication == null) {
            log.info(() -> "authentication is null.");
        } else {
            log.info(() -> "authentication.getName(): " + authentication.getName());

            authentication.getAuthorities().forEach(item -> {
                log.info(() -> item.toString());
            });
        }

        return "{\"status\":\"OK\"}\n";
    }

    @GetMapping(path = "/employees")
    @PreAuthorize("hasRole('ADMIN')")
    public Employees getEmployees() {

        log.info(() -> "Called getEmployees()");

        return employeeDao.getAllEmployees();
    }

    @PostMapping(path = "/employees", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addEmployee(
            @RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", defaultValue = "ASIA") String headerLocation,
            @RequestBody Employee employee)
        throws Exception {

        log.info(() -> "Called addEmployee()");

        //Generate resource id
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);

        //add resource
        employeeDao.addEmployee(employee);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId()).toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
