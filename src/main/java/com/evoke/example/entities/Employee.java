package com.evoke.example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employee")
@DynamicUpdate(true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "emp_name")
    private String name;
    @Column(name = "emp_salary")
    private Integer salary;
    @Column(name = "emp_age")
    private Integer age;
    @Column(name = "emp_address")
    private String address;
    private String street;
    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    private Set<Course> courses;


}