package com.evoke.example.service;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import com.evoke.example.mapper.EmployeeMapper;
import com.evoke.example.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @MockBean
    private EmployeeRepository empRepo;
    @MockBean
    private EmployeeService empService;
    @MockBean
    EmployeeMapper employeeMapper;
    Employee emp;
    EmployeeDTO empDTO;

    @BeforeEach
    public void setup() {
        emp = Employee.builder()
                .id(1)
                .address("hyd")
                .age(23)
                .salary(2323)
                .name("sls")
                .build();
        empDTO = EmployeeDTO.builder()
                .id(1)
                .address("hyd")
                .age(23)
                .salary(2323)
                .name("sls")
                .build();
    }

    @Test
    public void saveEmpTest() {
        given(empRepo.save(emp)).willReturn(emp);
        EmployeeDTO savedEmployee = empService.saveEmp(empDTO);
        System.out.println(savedEmployee);
        //assertThat(savedEmployee).isEqualTo(any(Employee.class));
        assertThat(empService.saveEmp(empDTO)).isEqualTo(savedEmployee);
    }

    @Test
    public void saveAllEmpsTest() {
        Employee emp1 = Employee.builder()
                .id(2)
                .address("hyd")
                .age(23)
                .salary(2323)
                .name("sls")
                .build();
        EmployeeDTO empDTO1 = EmployeeDTO.builder()
                .id(2)
                .address("hyd")
                .age(23)
                .salary(2323)
                .name("sls")
                .build();
        List<Employee> lemp = new ArrayList<>();
        lemp.add(emp);
        lemp.add(emp1);
        List<EmployeeDTO> lempDto = new ArrayList<>();
        lempDto.add(empDTO);
        lempDto.add(empDTO1);
        given(empRepo.saveAll(lemp)).willReturn(lemp);
        List<Employee> savedEmps = empService.saveEmps(lempDto);
        System.out.println(savedEmps);
        assertThat(empService.saveEmps(lempDto)).isEqualTo(savedEmps);

    }

//    @Test
//    public void findAllTests() {
//        Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
//        Employee emp1 = Employee.builder()
//                .id(2)
//                .address("hyd")
//                .age(23)
//                .salary(2323)
//                .name("sls")
//                .build();
//        List<Employee> lemp = new ArrayList<>();
//        lemp.add(emp);
//        lemp.add(emp1);
//        given(empRepo.findAll(firstPageWithTwoElements)).willReturn(lemp);
//        List<Employee> emps = (List)empRepo.findAll(firstPageWithTwoElements);
//        System.out.println(emps);
//        assertThat(empRepo.findAll()).isEqualTo(emps);
//    }

    @Test
    public void findByIdTest() {
        int id = 1;
        given(empRepo.findById(id)).willReturn(emp);
        Employee e = empRepo.findById(id);
        System.out.println(e);
        assertThat(empRepo.findById(id)).isEqualTo(e);
    }

    @Test
    public void updateEmpTest() throws JsonMappingException {

        given(empRepo.save(emp)).willReturn(emp);
        int k = empDTO.getId();
        Employee savedEmployee = empRepo.findById(k);
        savedEmployee = employeeMapper.toEntity(empDTO);
        empRepo.save(savedEmployee);
        System.out.println(savedEmployee);
        assertThat(empService.updateEmp(empDTO)).isEqualTo(savedEmployee);

    }

    @Test
    public void deleteEmpTest() {
        int k = 1;
        willDoNothing().given(empRepo).deleteById(k);
        empRepo.deleteById(k);
        verify(empRepo, times(1)).deleteById(k);

    }


}
