package com.evoke.example.dto;

import com.evoke.example.entities.Employee;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
@Setter
@Getter
@Builder

public class CourseDTO {
    private Integer course_id;
    private String course_name;
    private Integer course_duration;
    //@JsonIgnore
    private Employee employee;
}
