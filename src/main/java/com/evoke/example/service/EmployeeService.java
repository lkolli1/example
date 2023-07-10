package com.evoke.example.service;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import com.evoke.example.mapper.EmployeeMapper;
import com.evoke.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@CacheConfig(cacheNames={"employeeCache"})
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
        empRepo.saveAll(employeeMapper.toEntityList(empDTO));
        return employeeMapper.toEntityList(empDTO);//doubt
    }
    @Cacheable(value="employeeCache")
    public List<EmployeeDTO> findAll(Integer page,Integer size) {
        Pageable firstPageWithElements = PageRequest.of(page, size,Sort.by("id"));
        return employeeMapper.toDtoList(empRepo.findAll(firstPageWithElements).stream().toList());
    }
    @Cacheable(value="employeeCache",key="#id")
    public EmployeeDTO findById(int id) {
        Employee employee = empRepo.findById(id);
        EmployeeDTO empDTO = new EmployeeDTO();
        empDTO = employeeMapper.toDto(employee);
        return empDTO;

    }
    @Cacheable(value="employeeCache",key="#id",unless="#result==null")
    public EmployeeDTO finByName(String name) {
        return employeeMapper.toDto(empRepo.findByName(name));

    }

    public List<EmployeeDTO> findStreams() {
        Pageable firstPageWithTwoElements = PageRequest.of(1, 3);
        return employeeMapper.toDtoList(empRepo.findAll(firstPageWithTwoElements).stream()
                .filter(e -> e.getSalary() > 10000)
                .toList());
    }

    public List<EmployeeDTO> findSorted() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
       return employeeMapper.toDtoList(empRepo.findAll(firstPageWithTwoElements).stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).toList());
    }

    public void findIncrement() {
        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
        empRepo.findAll(firstPageWithTwoElements).stream()
                .filter(e -> e.getSalary() < 10000)
                .forEach(this::getIncrement);

    }

    public void getIncrement(Employee employee) {
        Integer salary = employee.getSalary();
        employee.setSalary(salary * 10);
    }

    @Transactional
    @CachePut(value="employeeCache")
    public Employee updateEmp(EmployeeDTO empDTO) {
        int k = empDTO.getId();
        Employee employee = empRepo.findById(k);
        employee = employeeMapper.toEntity(empDTO);//if i dont send some fields that fields becoming null
        empRepo.save(employee);
        return employee;
    }
    @CacheEvict(value="employeeCache")
    public String deleteEmp(Integer id) {

        empRepo.deleteById(id);
        if (empRepo.findById(id).isEmpty())
            return "Employee  Deleted";
        else
            return "Employee Not Deleted";
    }


}