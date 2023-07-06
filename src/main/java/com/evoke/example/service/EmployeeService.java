package com.evoke.example.service;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import com.evoke.example.mapper.EmployeeMapper;
import com.evoke.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepo;
    @Resource
    EmployeeMapper employeeMapper;

    @Transactional
    public Employee saveEmp(EmployeeDTO empDTO) {
        Employee emp = employeeMapper.toEntity(empDTO);
        empRepo.save(emp);
        return emp;

    }

    @Transactional
    public List<Employee> saveEmps(List<EmployeeDTO> empDTO) {
        List<Employee> empList = empDTO.stream().map(x -> {
            Employee employee = employeeMapper.toEntity(x);
            return employee;
        }).collect(toList());
        empRepo.saveAll(empList);
        return empList;
    }

    public List<EmployeeDTO> findAll(Integer page,Integer size) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        return
                empRepo.findAll(firstPageWithTwoElements).stream().map(emp -> {
                    EmployeeDTO empDTO = new EmployeeDTO();
                    empDTO = employeeMapper.toDto(emp);
                    return empDTO;
                }).collect(toList());

    }

    public EmployeeDTO findById(int id) {
        Employee employee = empRepo.findById(id);
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO = employeeMapper.toDto(employee);
        return empDTO;

    }

    public EmployeeDTO finByName(String name) {
        Employee employee = empRepo.findByName(name);
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO = employeeMapper.toDto(employee);
        return empDTO;
    }

    public List<EmployeeDTO> findStreams() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        return empRepo.findAll(firstPageWithTwoElements).stream()
                .filter(e -> e.getSalary() > 10000)
                .map(emp -> {
                    EmployeeDTO empDTO = new EmployeeDTO();
                    empDTO = employeeMapper.toDto(emp);
                    return empDTO;
                }).collect(Collectors.toList());
    }

    public List<EmployeeDTO> findSorted() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
       return empRepo.findAll(firstPageWithTwoElements).stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName()))
                .map(emp -> {
                    EmployeeDTO empDTO = new EmployeeDTO();
                    empDTO = employeeMapper.toDto(emp);
                    return empDTO;
                }).collect(Collectors.toList());

    }

    public void findIncrement() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        empRepo.findAll(firstPageWithTwoElements).stream()
                .filter(e -> e.getSalary() < 10000)
                .forEach(e -> getIncrement(e));

    }

    public void getIncrement(Employee employee) {
        Integer salary = employee.getSalary();
        employee.setSalary(salary * 10);
    }

    @Transactional
    public Employee updateEmp(EmployeeDTO empDTO) {
        int k = empDTO.getId();
        Employee employee = empRepo.findById(k);
        employee = employeeMapper.toEntity(empDTO);
        empRepo.save(employee);
        return employee;
    }

    public String deleteEmp(Integer id) {

        empRepo.deleteById(id);
        if (empRepo.findById(id).isEmpty())
            return "Employee  Deleted";
        else
            return "Employee Not Deleted";
    }


}