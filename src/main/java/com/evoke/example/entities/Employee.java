package com.evoke.example.entities;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Employee")
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
    @Column(name="id")
    private Integer id;
    @Column(name="emp_name")
    private String name;
    @Column(name="emp_salary")
    private Integer salary;
    @Column(name="emp_age")
    private Integer age;
    @Column(name="emp_address")
    private String address;
    private String street;


}