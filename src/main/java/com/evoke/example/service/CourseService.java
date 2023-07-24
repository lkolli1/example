package com.evoke.example.service;

import com.evoke.example.dto.CourseDTO;
import com.evoke.example.entities.Course;
import com.evoke.example.entities.Employee;
import com.evoke.example.mapper.CourseMapper;
import com.evoke.example.repository.CourseRepository;
import com.evoke.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    CourseRepository courseRepository;
    @Resource
    EmployeeRepository employeeRepository;
    @Transactional
    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Employee employee = employeeRepository.findById(courseDTO.getEmployee().getId()).orElse(null);
        course.setEmployee(employee);
        courseRepository.save(course);
        return courseMapper.toDto(course);
    }
    @Transactional
    public List<CourseDTO> saveCourses(List<CourseDTO> courseDTOList) {
        List<Course> courseList = courseMapper.toEntityList(courseDTOList);
        courseList.stream().forEach(course -> {
            course.setEmployee(employeeRepository.findById(course.getId()).orElse(null));
        });
        courseRepository.saveAll(courseList);
        return courseMapper.toDtoList(courseList);
    }
    @Transactional(readOnly = true)
    public CourseDTO getCourse(Integer id) {
        Course course=courseRepository.findById(id).orElse(null);
        Employee employee=employeeRepository.findById(course.getEmployee().getId()).orElse(null);
        course.setEmployee(employee);
        return courseMapper.toDto(course);
    }
    @Transactional(readOnly = true)
    public List<CourseDTO> getCourses() {
        return courseMapper.toDtoList(courseRepository.findAll());
    }
}
