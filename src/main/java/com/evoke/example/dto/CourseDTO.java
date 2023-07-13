package com.evoke.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class CourseDTO {
    private Integer course_id;
    private String course_name;
    private Integer course_duration;
    private Integer id;
}
