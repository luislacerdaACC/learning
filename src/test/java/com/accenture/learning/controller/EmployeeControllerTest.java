package com.accenture.learning.controller;

import com.accenture.learning.repository.EmployeeRepository;
import com.accenture.learning.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SqlGroup({
        @Sql(value = "classpath:db/employee-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository repository;


    @Test
    public void list() throws Exception {

        String expectedResponse = FileUtil.readFromFileToString("/api/employee/listExpectedResult.json");

        mvc.perform(MockMvcRequestBuilders.get("/employee").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void get() throws Exception {

        String expectedResponse = FileUtil.readFromFileToString("/api/employee/getExpectedResult.json");

        mvc.perform(MockMvcRequestBuilders.get("/employee/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void get_notFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/employee/10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update() throws Exception {

        String input = FileUtil.readFromFileToString("/api/employee/input/updateInput.json");
        String expectedResponse = FileUtil.readFromFileToString("/api/employee/updateExpectedResult.json");

        mvc.perform(MockMvcRequestBuilders.put("/employee")
                        .content(input)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void merge() throws Exception {

        String input = FileUtil.readFromFileToString("/api/employee/input/patchInput.json");
        String expectedResponse = FileUtil.readFromFileToString("/api/employee/patchExpectedResult.json");

        mvc.perform(MockMvcRequestBuilders.patch("/employee")
                        .content(input)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void insert() throws Exception {

        String input = FileUtil.readFromFileToString("/api/employee/input/insertInput.json");
        String expectedResponse = FileUtil.readFromFileToString("/api/employee/insertExpectedResult.json");

        mvc.perform(MockMvcRequestBuilders.post("/employee")
                        .content(input)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void delete() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete("/employee/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(this.repository.findAll().size(), equalTo(1));
    }
}
