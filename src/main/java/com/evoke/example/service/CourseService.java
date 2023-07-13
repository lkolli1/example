package com.evoke.example.service;

import com.evoke.example.dto.CourseDTO;
import com.evoke.example.entities.Course;
import com.evoke.example.entities.Employee;
import com.evoke.example.mapper.CourseMapper;
import com.evoke.example.repository.CourseRepository;
import com.evoke.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

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

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Employee employee = employeeRepository.findById(courseDTO.getId()).orElse(null);
        course.setEmployee(employee);
        courseRepository.save(course);
        return courseMapper.toDto(course);

    }

    public List<CourseDTO> saveCourses(List<CourseDTO> courseDTOList) {
        List<Course> courseList = courseMapper.toEntityList(courseDTOList);
        courseList.stream().forEach(course -> {
            course.setEmployee(employeeRepository.findById(course.getId()).orElse(null));
        });
        courseRepository.saveAll(courseList);
        return courseMapper.toDtoList(courseList);
    }

    public CourseDTO getCourse(Integer id) {
        return courseMapper.toDto(courseRepository.findById(id).orElse(null));
    }

    public List<CourseDTO> getCourses() {
        return courseMapper.toDtoList(courseRepository.findAll());
    }
}
