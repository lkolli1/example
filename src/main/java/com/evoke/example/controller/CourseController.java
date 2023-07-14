package com.evoke.example.controller;

import com.evoke.example.dto.CourseDTO;
import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CourseController {
    @Resource
    CourseService courseService;

    @PostMapping("/course")
    @ResponseBody
    public ResponseEntity<String> saveCourse(@RequestBody CourseDTO courseDTO) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(courseService.saveCourse(courseDTO), resHeaders, HttpStatus.CREATED);

    }

    @PostMapping("/courses")
    @ResponseBody
    public ResponseEntity<String> saveCourses(@RequestBody List<CourseDTO> courseDTOList) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(courseService.saveCourses(courseDTOList), resHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/course/{id}")
    @ResponseBody
    public ResponseEntity<CourseDTO> getCourse(@PathVariable(value = "id") Integer id){
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(courseService.getCourse(id), resHeaders, HttpStatus.OK);
    }

    @GetMapping("/courses")
    @ResponseBody
    public List<CourseDTO> getCourses(){
        return courseService.getCourses();
    }

}
