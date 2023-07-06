package com.evoke.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;

import lombok.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
//validations@pattern
@Builder
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
public class EmployeeDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    @Size(min = 3, message = "Name should be minimum three characters")
    private String name;
    @JsonProperty("salary")
    @NotBlank(message = "Blank Not Allowed")
    @Pattern(regexp = "^[0-9]*", message = "Should be Digits Only")
    @Min(value = 10000, message = "Salary Should be Greater than 10000")
    private Integer salary;
    @JsonProperty("age")
    @Min(value = 18, message = "Age should be greater than are equal to 18")
    @Max(value = 60, message = "Age should be less than 60")
    @Pattern(regexp = "^[0-9]*", message = "Should be Digits Only")
    private Integer age;
    @JsonProperty("address")
    private String address;
    private String road;


}