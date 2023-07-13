package com.evoke.example.mapper;

import com.evoke.example.dto.CourseDTO;
import com.evoke.example.entities.Course;
import com.evoke.example.entities.Employee;
import org.mapstruct.Mapper;
import java.util.List;
@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseDTO courseDTO);
    CourseDTO toDto(Course course);
    List<Course> toEntityList(List<CourseDTO> courseDTOList);
    List<CourseDTO> toDtoList(List<Course> courseList);

}
