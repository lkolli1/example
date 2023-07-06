package com.evoke.example.controller;
import com.evoke.example.dto.EmployeeDTO;
import com.evoke.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
   private EmployeeService empService;
    @PostMapping("/employee")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.saveEmp(employeeDTO), resHeaders, HttpStatus.CREATED);
    }
    @PostMapping("/employees")
    public ResponseEntity<String> saveEmployees(@RequestBody List<EmployeeDTO> empList) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.saveEmps(empList), resHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/employees/{page}/{size}")
    public List<EmployeeDTO> findAll(@PathVariable(value="page",required = false) Integer page,
                                     @PathVariable(value="size",required = false)Integer size) {
        if(page.intValue()<=0)
            page=1;
        if(size.intValue()<=0)
            size=2;

        return empService.findAll(page,size);
    }
    @RequestMapping(value="/employee/{id}", method=RequestMethod.GET)
    @ResponseBody
    public EmployeeDTO findById(@PathVariable(value="id") Integer id) {
        return empService.findById(id);
    }
    @RequestMapping(value="/employees/{name}",method=RequestMethod.GET)
    @ResponseBody
    public EmployeeDTO findByName(@PathVariable(value="name") String name){return empService.finByName(name);}
    @PutMapping("/employee")
    public ResponseEntity<String> updateEmp(@RequestBody EmployeeDTO empDto) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.updateEmp(empDto), resHeaders, HttpStatus.CREATED);
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Integer> deleteEmp(@PathVariable int id) {
        HttpHeaders resHeaders = new HttpHeaders();
        return new ResponseEntity(empService.deleteEmp(id), resHeaders, HttpStatus.OK);
    }
    @GetMapping("/employee/stream")
    @ResponseBody
    public List<EmployeeDTO> findStreams()
    {
        return empService.findStreams();
    }
    @GetMapping("/employee/increment")
    @ResponseBody
    public void findStreamsIncrement()
    {
         empService.findIncrement();
    }
    @GetMapping("/employee/sort")
    @ResponseBody
    public List<EmployeeDTO> findSorted()
    {
        return empService.findSorted();
    }
}