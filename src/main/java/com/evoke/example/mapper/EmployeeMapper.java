package com.evoke.example.mapper;

import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target="street", source="employeeDTO.road")
    Employee toEntity(EmployeeDTO employeeDTO);

    @Mapping(target="road", source="employee.street")
    EmployeeDTO toDto(Employee employee);

}
