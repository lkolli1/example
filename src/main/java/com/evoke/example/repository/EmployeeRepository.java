package com.evoke.example.repository;

import com.evoke.example.entities.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Integer> {
    public Employee findById(int id);
    public Employee findByName(String name);

}
