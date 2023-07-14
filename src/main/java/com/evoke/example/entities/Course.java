package com.evoke.example.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="course")
@DynamicUpdate(true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Course {
    @Id
    @GeneratedValue
    private Integer course_id;
    @Column(name="course_name")
    private String course_name;
    @Column(name="course_duration")
    private Integer course_duration;
    @Column(name="emp_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="id", nullable = false)
    @JsonIgnore
    @JsonManagedReference
    private Employee employee;
}


