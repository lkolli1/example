package com.evoke.example.service;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import com.evoke.example.exception.CustomAccessDeniedHandler;
import com.evoke.example.exception.EmployeeNotFoundException;
import com.evoke.example.mapper.EmployeeMapper;
import com.evoke.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.List;


@Service
@CacheConfig(cacheNames = {"employeeCache"})
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepo;
    @Resource
    EmployeeMapper employeeMapper;


    @Transactional
    public EmployeeDTO saveEmp(EmployeeDTO empDTO) {
        Employee emp = employeeMapper.toEntity(empDTO);
        empRepo.save(emp);
        return employeeMapper.toDto(emp);
    }

    @Transactional
    public List<Employee> saveEmps(List<EmployeeDTO> empDTO) {
        empRepo.saveAll(employeeMapper.toEntityList(empDTO));
        return employeeMapper.toEntityList(empDTO);
    }

    @Cacheable(value = "employeeCache")
    @Transactional(readOnly=true)
    public List<EmployeeDTO> findAll(Integer page, Integer size) {

        return employeeMapper.toDtoList(empRepo.findAll().stream().toList());
    }

    @Cacheable(value = "employeeCache", key = "#id")
    @Transactional(readOnly=true)
    public EmployeeDTO findById(int id) {

            Employee employee = empRepo.findById(id).orElse(null);
            if(null==employee)
                throw new EmployeeNotFoundException("Employee does not exits with given id :"+id);
        return employeeMapper.toDto(employee);



    }

    @Cacheable(value = "employeeCache", key = "#id", unless = "#result==null")
    @Transactional(readOnly=true)
    public EmployeeDTO finByName(String name) {
       if(empRepo.existsByName(name)) return employeeMapper.toDto(empRepo.findByName(name));
       else {
           throw new EmployeeNotFoundException("Employee does not exits with given Name"+name);
       }


    }
    @Transactional(readOnly=true)
    public List<EmployeeDTO> findStreams() {
        return employeeMapper.toDtoList(empRepo.findAll().stream()
                .filter(e -> e.getSalary() > 10000)
                .toList());
    }
    @Transactional(readOnly=true)
    public List<EmployeeDTO> findSorted() {
        return employeeMapper.toDtoList(empRepo.findAll().stream()
                .sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).toList());
    }
    @Transactional(readOnly=true)
    public void findIncrement() {

        empRepo.findAll().stream()
                .filter(e -> e.getSalary() < 10000)
                .map(e -> e.getSalary() * 10).toList();
    }
    @Transactional
    @CachePut(value = "employeeCache")
    public Employee updateEmp(EmployeeDTO empDTO) {
        int k = empDTO.getId();
        Employee employee = empRepo.findById(k).orElse(null);
        employee = toEntityUpdate(empDTO,employee);
        empRepo.save(employee);
        return employee;
    }
    @Transactional
    @CacheEvict(value = "employeeCache")
    public String deleteEmp(Integer id) {

        empRepo.deleteById(id);
        if (empRepo.findById(id).isEmpty())
            return "Employee  Deleted";
        else
            return "Employee Not Deleted";
    }

    private Employee toEntityUpdate(EmployeeDTO employeeDTO, Employee employee) {

        if (!StringUtils.hasLength(employeeDTO.getRoad()) && !employee.getStreet().equals(employeeDTO.getRoad()))
            employee.setStreet(employeeDTO.getRoad());
        if (!StringUtils.hasLength(employeeDTO.getName()) && !employee.getName().equals(employeeDTO.getName()))
            employee.setName(employeeDTO.getName());
        if (null != employeeDTO.getSalary() && employee.getSalary() != (employeeDTO.getSalary().intValue()))
            employee.setSalary(employeeDTO.getSalary());
        if (null != employeeDTO.getAge() && employee.getAge() != (employeeDTO.getAge().intValue()))
            employee.setAge(employeeDTO.getAge());
        if (!StringUtils.hasLength(employeeDTO.getAddress()) && !employee.getAddress().equals(employeeDTO.getAddress()))
            employee.setAddress(employeeDTO.getAddress());

        return employee;
    }
}