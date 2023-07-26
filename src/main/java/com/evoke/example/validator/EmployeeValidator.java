package com.evoke.example.validator;


import com.evoke.example.dto.EmployeeDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.util.StringUtils.hasLength;

@Component
public class EmployeeValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(EmployeeDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO emp = (EmployeeDTO) target;
        if(!hasLength(emp.getName())){
            errors.rejectValue("name", "nullValue", new Object[]{"'name'"}, "name is required");
        }

    }
}
