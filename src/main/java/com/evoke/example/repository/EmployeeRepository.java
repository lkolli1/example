package com.evoke.example.repository;

import com.evoke.example.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    
    boolean existsByName(String name);

    Employee findByName(String name);
}
