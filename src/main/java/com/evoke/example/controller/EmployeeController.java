package com.evoke.example.controller;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import com.evoke.example.exception.EmployeeNotFoundException;
import com.evoke.example.exception.InvalidTokenException;
import com.evoke.example.exception.RoleMismatchException;
import com.evoke.example.service.EmployeeService;
import com.evoke.example.validator.EmployeeValidator;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService empService;

    @Autowired
    EmployeeValidator employeeValidator;

    @PostMapping("/employee")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<Object> saveEmployee(@Valid @RequestBody EmployeeDTO employee, BindingResult errors) {
        HttpHeaders resHeaders = new HttpHeaders();
        employeeValidator.validate(employee, errors);
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), resHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(empService.saveEmp(employee), resHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/employees")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<String> saveEmployees(@RequestBody List<EmployeeDTO> empList, BindingResult errors) {
        try {
            HttpHeaders resHeaders = new HttpHeaders();

            return new ResponseEntity(empService.saveEmps(empList), resHeaders, HttpStatus.CREATED);
        } catch (RoleMismatchException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (InvalidTokenException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/employees/{page}/{size}")
    @PreAuthorize("hasRole('ROLE_HR')")
    public List<EmployeeDTO> findAll(@PathVariable(value = "page", required = false) Integer page,
                                     @PathVariable(value = "size", required = false) Integer size) {
        return empService.findAll(page, size);
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EmployeeDTO findById(@PathVariable(value = "id") Integer id) {
        return empService.findById(id);
    }

    @RequestMapping(value = "/employees/{name}", method = RequestMethod.GET)
    @ResponseBody
    public EmployeeDTO findByName(@PathVariable(value = "name") String name) {
        return empService.finByName(name);
    }

    @PutMapping("/employee")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<String> updateEmp(@RequestBody EmployeeDTO empDto) throws JsonMappingException {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.updateEmp(empDto), resHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('ROLE_HR')")
    public ResponseEntity<Integer> deleteEmp(@PathVariable int id) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.deleteEmp(id), resHeaders, HttpStatus.OK);
    }

    @GetMapping("/employee/stream")
    @ResponseBody
    public List<EmployeeDTO> findStreams() {
        return empService.findStreams();
    }

    @GetMapping("/employee/increment")
    @ResponseBody
    public void findStreamsIncrement() {
        empService.findIncrement();
    }

    @GetMapping("/employee/sort")
    @ResponseBody
    public List<EmployeeDTO> findSorted() {
        return empService.findSorted();
    }
}