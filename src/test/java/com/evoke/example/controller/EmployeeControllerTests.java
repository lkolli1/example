package com.evoke.example.controller;

import com.evoke.example.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTests {
    @MockBean
    private EmployeeService empService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findALLTests() {
        String Uri = "/employees";

    }

}
